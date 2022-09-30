package com.wodder.product.application;

import com.wodder.product.domain.model.category.Category;
import com.wodder.product.domain.model.category.CategoryId;
import com.wodder.product.domain.model.category.CategoryRepository;
import com.wodder.product.domain.model.product.ExternalId;
import com.wodder.product.domain.model.product.Price;
import com.wodder.product.domain.model.product.Product;
import com.wodder.product.domain.model.product.ProductCreated;
import com.wodder.product.domain.model.product.ProductId;
import com.wodder.product.domain.model.product.ProductName;
import com.wodder.product.domain.model.product.ProductRepository;
import com.wodder.product.domain.model.product.Quantity;
import com.wodder.product.domain.model.product.UnitOfMeasurement;
import com.wodder.product.domain.model.shipment.LineItem;
import com.wodder.product.domain.model.shipment.Shipment;
import com.wodder.product.domain.model.shipment.ShipmentDomainService;
import com.wodder.product.domain.model.shipment.ShipmentId;
import com.wodder.product.domain.model.shipment.ShipmentRepository;
import com.wodder.product.dto.ProductDto;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.enterprise.event.Event;
import javax.inject.Inject;

public class ProductServiceImpl implements ProductService {

  private final ProductRepository productRepository;
  private final CategoryRepository categoryRepository;
  private final ShipmentRepository shipmentRepository;
  private final Event<ProductCreated> productCreatedEvent;

  @Inject
  public ProductServiceImpl(
      ProductRepository productRepository,
      CategoryRepository categoryRepository,
      ShipmentRepository shipmentRepository,
      Event<ProductCreated> productCreatedEvent) {
    this.productRepository = productRepository;
    this.categoryRepository = categoryRepository;
    this.shipmentRepository = shipmentRepository;
    this.productCreatedEvent = productCreatedEvent;
  }

  @Override
  public Optional<ProductDto> createNewProduct(ProductDto newItem) {
    return createNewProduct(
        newItem.getName(),
        newItem.getCategory(),
        newItem.getUnits(),
        newItem.getUnitsPerCase(),
        newItem.getItemPrice(),
        newItem.getCasePrice());
  }

  @Override
  public Optional<ProductDto> createNewProduct(
      String name,
      String category,
      String unit,
      int unitsPerCase,
      String unitPrice,
      String casePrice) {
    Category cat =
        categoryRepository
            .loadByItem(new Category(category))
            .orElseGet(() -> categoryRepository.createItem(new Category(category)));

    Product item =
        new Product(
            name,
            cat,
            new UnitOfMeasurement(unit),
            new Price(unitPrice));

    return Optional.of(productRepository.createItem(item)).map(Product::toItemModel);
  }

  @Override
  public String createProduct(CreateProductCommand cmd) {
    Optional<Category> opt =
        categoryRepository.loadById(CategoryId.categoryIdOf(cmd.getCategoryId()));

    if (opt.isPresent()) {
      Product product =
          Product.builder(cmd.getName())
              .withCategory(opt.get())
              .withExternalId(cmd.getExternalId())
              .withUnitPrice(cmd.getUnitPrice())
              .withUnitsOfMeasurement(cmd.getUnitMeasurement())
              .build();
      String id = productRepository.createItem(product).getId().getValue();
      productCreatedEvent.fire(
          new ProductCreated(product.getId(), product.getName(), product.getUnits()));
      return id;
    } else {
      throw new IllegalArgumentException(
          String.format("Unknown categoryId{ %s }", cmd.getCategoryId()));
    }
  }

  @Override
  public Boolean deleteProduct(String productId) {
    return productRepository.deleteItem(ProductId.productIdOf(productId));
  }

  @Override
  public Optional<ProductDto> updateProductCategory(String productId, String category) {
    if (productId == null) {
      return Optional.empty();
    } else {
      return processCategoryUpdate(productId, category);
    }
  }

  @Override
  public Optional<ProductDto> updateProductName(String productId, String name) {
    Optional<Product> opt = productRepository.loadById(ProductId.productIdOf(productId));
    if (opt.isPresent()) {
      Product item = opt.get();
      item.updateName(ProductName.of(name));
      productRepository.updateItem(item);
      return Optional.of(item.toItemModel());
    } else {
      return Optional.empty();
    }
  }

  @Override
  public Optional<ProductDto> updateProductUnitOfMeasurement(
      String productId, String unitOfMeasurement) {
    Optional<Product> opt = productRepository.loadById(ProductId.productIdOf(productId));
    if (opt.isPresent()) {
      Product item = opt.get();
      item.updateUnitOfMeasurement(new UnitOfMeasurement(unitOfMeasurement));
      productRepository.updateItem(item);
      return Optional.of(item.toItemModel());
    } else {
      return Optional.empty();
    }
  }

  @Override
  public Optional<ProductDto> updateProductPrice(
      String productId, String unitPrice, String casePrice) {
    Optional<Product> opt = productRepository.loadById(ProductId.productIdOf(productId));
    if (opt.isPresent()) {
      Product i = opt.get();
      if (unitPrice != null) {
        i.updateUnitPrice(Price.of(unitPrice));
      }
      if (casePrice != null) {
        i.updateCasePrice(Price.of(casePrice));
      }
      productRepository.updateItem(i);
      return Optional.of(i.toItemModel());
    } else {
      return Optional.empty();
    }
  }

  private Optional<ProductDto> processCategoryUpdate(String inventoryItemId, String category) {
    Optional<Product> inventoryItem =
        productRepository.loadById(ProductId.productIdOf(inventoryItemId));
    Optional<Category> c = categoryRepository.loadByItem(new Category(category));
    if (inventoryItem.isPresent() && c.isPresent()) {
      Product item = inventoryItem.get();
      item.updateCategory(c.get());
      return productRepository.updateItem(item).map(Product::toItemModel);
    } else {
      return Optional.empty();
    }
  }

  @Override
  public Optional<ProductDto> loadProductById(String productId) {
    if (productId == null) {
      return Optional.empty();
    }
    return productRepository.loadById(ProductId.productIdOf(productId)).map(Product::toItemModel);
  }

  @Override
  public Optional<ProductDto> loadProductByExternalId(String externalId) {
    if (externalId == null || externalId.isBlank()) {
      return Optional.empty();
    }
    return productRepository.loadByExternalId(ExternalId.of(externalId)).map(Product::toItemModel);
  }

  @Override
  public Boolean receiveShipmentOfProducts(ReceiveShipmentCommand cmd) {
    ShipmentId id = ShipmentId.of(cmd.getShipmentId());
    Optional<Shipment> opt = shipmentRepository.loadById(id);
    if (opt.isEmpty()) {
      Shipment s = ShipmentDomainService.addLineItems(Shipment.from(id), cmd);
      shipmentRepository.createItem(s);

      for (LineItem e : s.getLineItems()) {
        ExternalId externalId = ExternalId.of(e.getId().getValue());
        Optional<Product> p = productRepository.loadByExternalId(externalId);
        Product product = p.orElseGet(() ->
            productRepository.createItem(
                Product.builder(e.getName())
                    .withExternalId(externalId.getValue())
                    .withCategory(Category.defaultCategory())
                    .withUnitsOfMeasurement(e.getUnits().getValue())
                    .withUnitPrice(e.getPrice().getItemPrice())
                    .build()
            ));

        product.receiveQty(Quantity.of(e.getNumberOfPieces()));
      }
      return true;
    } else {
      throw new IllegalArgumentException("Shipment with id {} was already received");
    }
  }

  @Override
  public List<ProductDto> loadAllProducts() {
    return productRepository.loadAllItems().stream()
        .map(Product::toItemModel)
        .collect(Collectors.toList());
  }
}
