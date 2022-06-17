package com.wodder.inventory.application.implementations;

import com.wodder.inventory.application.InventoryService;
import com.wodder.inventory.application.ProductService;
import com.wodder.inventory.domain.model.inventory.Inventory;
import com.wodder.inventory.domain.model.inventory.InventoryCount;
import com.wodder.inventory.domain.model.inventory.InventoryId;
import com.wodder.inventory.domain.model.inventory.Item;
import com.wodder.inventory.domain.model.inventory.report.InventoryReport;
import com.wodder.inventory.dto.InventoryCountDto;
import com.wodder.inventory.dto.InventoryDto;
import com.wodder.inventory.dto.ProductDto;
import com.wodder.inventory.dto.ReportDto;
import com.wodder.inventory.persistence.InventoryRepository;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

public class InventoryServiceImpl implements InventoryService {

  private final InventoryRepository repository;
  private final ProductService productService;

  InventoryServiceImpl(
      InventoryRepository repository,
      ProductService productService) {
    this.repository = repository;
    this.productService = productService;
  }

  @Override
  public InventoryDto createInventory() {
    Inventory i =
        Inventory.createNewInventoryWithProducts(
            this.productService.loadAllActiveProducts());
    return repository.createItem(i).toModel();
  }

  @Override
  public InventoryDto createEmptyInventory() {
    Inventory i = Inventory.createNewInventoryWithProducts(Collections.emptyList());
    return this.repository.createItem(i).toModel();
  }

  @Override
  public Optional<InventoryDto> addInventoryCount(
      String inventoryId, String productId, double units, double cases) {
    Optional<Inventory> opt = repository.loadById(InventoryId.inventoryIdOf(inventoryId));
    Optional<ProductDto> productOpt = productService.loadProduct(productId);
    if (opt.isPresent() && productOpt.isPresent()) {
      Inventory i = opt.get();
      Item item = Item.fromProductDto(productOpt.get());
      i.addItemToInventory(item.updateCount(new InventoryCount(units, cases)));
      return Optional.of(i.toModel());
    } else {
      return Optional.empty();
    }
  }

  @Override
  public Optional<InventoryDto> addInventoryCounts(
      String inventoryId, Collection<InventoryCountDto> counts) {
    Optional<Inventory> opt = repository.loadById(InventoryId.inventoryIdOf(inventoryId));
    if (opt.isPresent()) {
      Inventory i = opt.get();
      for (InventoryCountDto m : counts) {
        Optional<ProductDto> p = productService.loadProduct(m.getProductId());
        p.ifPresent(
            (product) ->
                i.addItemToInventory(
                    Item.fromProductDto(product)
                        .updateCount(new InventoryCount(m.getUnits(), m.getCases()))));
      }
      return Optional.of(i.toModel());
    } else {
      return Optional.empty();
    }
  }

  @Override
  public void saveInventory(InventoryDto model) {
    Inventory i = repository.loadById(InventoryId.inventoryIdOf(model.getId())).get();
    model
        .getItems()
        .forEach(
            itemModel -> {
              i.updateInventoryCount(
                  itemModel.getName(),
                  itemModel.getLocation(),
                  InventoryCount.countFrom(
                      itemModel.getNumberOfUnits(), itemModel.getNumberOfCases()));
            });
    repository.updateItem(i);
  }

  @Override
  public Optional<InventoryDto> loadInventory(String inventoryId) {
    Optional<Inventory> opt = repository.loadById(InventoryId.inventoryIdOf(inventoryId));
    if (opt.isPresent()) {
      return opt.map(Inventory::toModel);
    } else {
      return Optional.empty();
    }
  }

  @Override
  public ReportDto generateInventoryReport(LocalDate begin, LocalDate end) {
    Optional<Inventory> start = repository.getInventoryByDate(begin);
    Optional<Inventory> finish = repository.getInventoryByDate(end);
    InventoryReport report = InventoryReport.between(start.get(), finish.get());
    report.process();
    return report.toDto();
  }
}
