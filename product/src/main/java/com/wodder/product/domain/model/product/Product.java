package com.wodder.product.domain.model.product;

import com.wodder.product.domain.model.category.Category;
import com.wodder.product.dto.ProductDto;
import java.math.BigDecimal;

public class Product extends Entity<ProductId> {

  private ExternalId externalId;
  private ProductName name;
  private Category category;
  private UnitOfMeasurement uom;
  private Price price;
  private Quantity quantity = Quantity.zero();
  private boolean active;

  public Product(String name, Category category) {
    this(ProductId.generateId(), name, category, true, null, null, null);
  }

  public Product(
      String name,
      Category category,
      UnitOfMeasurement unitOfMeasurement,
      Price price) {
    this(ProductId.generateId(), name, category, true, unitOfMeasurement, price, null);
  }

  public Product(ProductId id, String name, Category category) {
    this(id, name, category, true, null, null, null);
  }

  public Product(
      ProductId id, String name, Category category, UnitOfMeasurement uom) {
    this(id, name, category, true, uom, null, null);
  }

  public Product(
      ProductId id,
      String name,
      Category category,
      UnitOfMeasurement uom,
      Price price) {
    this(id, name, category, true, uom, price, null);
  }

  public Product(
      ProductId id,
      ExternalId externalId,
      String name,
      Category category,
      UnitOfMeasurement unitOfMeasurement,
      Price price,
      Quantity quantity) {
    super(id);
    this.externalId = externalId;
    this.name = ProductName.of(name);
    this.category = category;
    this.uom = unitOfMeasurement;
    this.price = price;
    this.quantity = quantity;
  }

  public Product(Product that) {
    super(that.id);
    this.name = that.name;
    this.category = that.category;
    this.active = that.active;
    this.uom = that.uom;
    this.price = that.price;
    this.externalId = that.externalId;
    this.quantity = that.quantity;
  }

  private Product(
      ProductId id, String name, Category c, Boolean a, UnitOfMeasurement u,
      Price p, ExternalId externalId) {
    super(id);
    setName(name);
    setCategory(c);
    this.active = a;
    this.uom = u;
    this.price = p;
    this.externalId = externalId;
  }

  public static Product from(
      ExternalId ext, String name, Category cat, UnitOfMeasurement uom, Price price) {
    return new Product(ProductId.generateId(), name, cat, true, uom, price, ext);
  }

  public ProductName getName() {
    return name;
  }

  public void setName(String name) {
    this.name = ProductName.of(name);
  }

  public Category getCategory() {
    return category;
  }

  private void setCategory(Category c) {
    if (c == null) {
      throw new IllegalArgumentException("Category is required");
    }
    this.category = c;
  }

  public void updateCategory(Category category) {
    if (this.category.equals(category)) {
      throw new IllegalArgumentException("Cannot update to the same category");
    }
    setCategory(category);
  }

  public void updateName(String name) {
    if (this.name.equals(name)) {
      throw new IllegalArgumentException("Cannot update to the same name");
    }
    setName(name);
  }

  public void updateUnitOfMeasurement(UnitOfMeasurement uom) {
    if (this.uom.equals(uom)) {
      throw new IllegalArgumentException("Cannot update Unit of Measurement to the same value");
    }
    this.uom = uom;
  }

  public void updatePrice(Price price) {
    this.price = price;
  }

  public boolean isActive() {
    return active;
  }

  public void inactivate() {
    this.active = false;
  }

  public UnitOfMeasurement getUnits() {
    return uom;
  }

  public int getUnitsPerCase() {
    return uom.getItemsPerCase();
  }

  public BigDecimal getUnitPrice() {
    return price.getUnitPrice();
  }

  public BigDecimal getCasePrice() {
    return price.getCasePrice();
  }

  public Price getPrice() {
    return price;
  }

  public ExternalId getExternalId() {
    return externalId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Product that = (Product) o;
    return getName().equals(that.getName());
  }

  @Override
  public int hashCode() {
    return name.hashCode();
  }

  public ProductDto toItemModel() {
    ProductDto.ProductModelBuilder b =
        ProductDto.builder()
            .withId(this.id.getId())
            .withName(this.name.getValue())
            .withCategory(this.category.getName())
            .withQuantityOnHand(String.valueOf(quantity.getAmount()))
            .isActive(this.active);

    if (this.uom != null) {
      b.withUnitOfMeasurement(uom.getUnit()).withItemsPerCase(uom.getItemsPerCase());
    }
    if (this.price != null) {
      b.withItemPrice(price.getUnitPrice().toString())
          .withCasePrice(price.getCasePrice().toString());
    }
    if (this.externalId != null) {
      b.withExternalId(externalId.getId());
    }
    return b.build();
  }

  public void receiveQty(Quantity of) {
    this.quantity = quantity.add(of);
  }

  public Quantity getQtyOnHand() {
    return quantity;
  }
}
