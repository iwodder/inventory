package com.wodder.product.domain.model.product;

import com.wodder.product.domain.model.category.Category;
import com.wodder.product.dto.ProductDto;

public class Product extends Entity<ProductId> {

  private ExternalId externalId;
  private ProductName name;
  private Category category;
  private UnitOfMeasurement uom;
  private CasePack casePack;
  private Price unitPrice;
  private Price casePrice;
  private Quantity quantity = Quantity.zero();
  private boolean active;

  public Product(String name, Category category) {
    this(ProductId.generateId(), name, category, true, null, null, null);
  }

  public Product(
      String name,
      Category category,
      UnitOfMeasurement unitOfMeasurement,
      Price unitPrice) {
    this(ProductId.generateId(), name, category, true, unitOfMeasurement, unitPrice, null);
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
      Price unitPrice) {
    this(id, name, category, true, uom, unitPrice, null);
  }

  public Product(
      ProductId id,
      ExternalId externalId,
      String name,
      Category category,
      UnitOfMeasurement unitOfMeasurement,
      Price unitPrice,
      Quantity quantity) {
    super(id);
    this.externalId = externalId;
    this.name = ProductName.of(name);
    this.category = category;
    this.uom = unitOfMeasurement;
    this.unitPrice = unitPrice;
    this.quantity = quantity;
  }

  public Product(Product that) {
    super(that.id);
    this.name = that.name;
    this.category = that.category;
    this.active = that.active;
    this.uom = that.uom;
    this.unitPrice = that.unitPrice;
    this.casePrice = that.casePrice;
    this.externalId = that.externalId;
    this.quantity = that.quantity;
    this.casePack = that.casePack;
  }

  private Product(
      ProductId id, String name, Category c, Boolean a, UnitOfMeasurement u,
      Price p, ExternalId externalId) {
    super(id);
    setName(name);
    setCategory(c);
    this.active = a;
    this.uom = u;
    this.unitPrice = p;
    this.externalId = externalId;
  }

  private Product(Builder b) {
    super(b.id);
    this.name = b.name;
    this.externalId = b.extId;
    this.category = b.category;
    this.active = b.active;
    this.uom = b.uom;
    this.casePack = b.casePack;
    this.unitPrice = b.unitPrice;
    this.casePrice = b.casePrice;
    this.quantity = b.quantity;
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

  public void updateName(ProductName name) {
    if (this.name.equals(name)) {
      throw new IllegalArgumentException("Cannot update to the same name");
    }
    if (name == null) {
      throw new IllegalArgumentException("Name cannot be set to null");
    }
    this.name = name;
  }

  public void updateUnitOfMeasurement(UnitOfMeasurement uom) {
    if (this.uom.equals(uom)) {
      throw new IllegalArgumentException("Cannot update Unit of Measurement to the same value");
    }
    this.uom = uom;
  }

  public void updateUnitPrice(Price price) {
    this.unitPrice = price;
  }

  public void updateCasePrice(Price price) {
    this.casePrice = price;
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

  public Price getCasePrice() {
    return this.casePrice;
  }

  public Price getUnitPrice() {
    return unitPrice;
  }

  public ExternalId getExternalId() {
    return externalId;
  }

  public void receiveQty(Quantity of) {
    this.quantity = quantity.add(of);
  }

  public Quantity getQtyOnHand() {
    return quantity;
  }

  public void updateCasePack(CasePack casePack) {
    this.casePack = casePack;
  }

  public CasePack getCasePack() {
    return this.casePack;
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
    return id.equals(that.getId());
  }

  @Override
  public int hashCode() {
    return name.hashCode();
  }

  public ProductDto toItemModel() {
    ProductDto.ProductModelBuilder b =
        ProductDto.builder()
            .withId(this.id.getValue())
            .withName(this.name.getValue())
            .withCategory(this.category.getName())
            .withQuantityOnHand(String.valueOf(quantity.getAmount()))
            .isActive(this.active);

    if (this.uom != null) {
      b.withUnitOfMeasurement(uom.getUnit());
    }
    if (this.casePack != null) {
      b.withItemsPerCase(casePack.getValue());
    }
    if (this.unitPrice != null) {
      b.withItemPrice(unitPrice.getValue().toString());
    }
    if (this.casePrice != null) {
      b.withCasePrice(casePrice.getValue().toString());
    }
    if (this.externalId != null) {
      b.withExternalId(externalId.getValue());
    }
    return b.build();
  }

  public static Builder builder(ProductId id, ProductName name) {
    return new Builder(id, name);
  }

  public static Builder builder(ProductName name) {
    return new Builder(ProductId.generateId(), name);
  }

  public static class Builder {
    private ProductName name;
    private ProductId id;
    private ExternalId extId;
    private Category category;
    private boolean active;
    private UnitOfMeasurement uom;
    private Price unitPrice;
    private Price casePrice;
    private Quantity quantity;
    private CasePack casePack;

    private Builder(ProductId id, ProductName name) {
      if (name == null) {
        throw new IllegalArgumentException("ProductName is required.");
      }
      this.id = id;
      this.name = name;
    }

    public Builder withExternalId(ExternalId id) {
      this.extId = id;
      return this;
    }

    public Builder withCategory(Category c) {
      this.category = c;
      return this;
    }

    public Builder isActive(boolean a) {
      this.active = a;
      return this;
    }

    public Builder withUnitsOfMeasurement(UnitOfMeasurement uom) {
      this.uom = uom;
      return this;
    }

    public Builder withUnitPrice(Price p) {
      this.unitPrice = p;
      return this;
    }

    public Builder withCasePrice(Price p) {
      this.casePrice = p;
      return this;
    }

    public Builder withStockedCount(Quantity qty) {
      this.quantity = qty;
      return this;
    }

    public Builder withCasePack(String itemsPerCase) {
      this.casePack = CasePack.ofItemsPerCase(itemsPerCase);
      return this;
    }

    public Product build() {
      return new Product(this);
    }
  }
}
