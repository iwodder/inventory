package com.wodder.product.domain.model.shipment;

public class QtyRecieved {
  private final int units;
  private final int cases;

  private QtyRecieved(int units, int cases) {
    this.units = units;
    this.cases = cases;
  }

  public static QtyRecieved from(String units, String cases) {
    return new QtyRecieved(
        Integer.parseInt(units),
        Integer.parseInt(cases)
    );
  }

  public String calculatePieces(CasePack pack) {
    int numOfPieces = units + (cases * pack.getItemsPerCase());
    return String.valueOf(numOfPieces);
  }
}
