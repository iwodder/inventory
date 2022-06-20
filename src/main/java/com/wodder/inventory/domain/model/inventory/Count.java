package com.wodder.inventory.domain.model.inventory;

import com.wodder.inventory.dto.CountDto;
import java.util.Objects;

public class Count {

  private static final Count ZERO = new Count(0.00, 0.00);
  private double units;
  private double cases;

  private Count(double units, double cases) {
    setUnits(units);
    setCases(cases);
  }

  public Count(Count that) {
    this(that.getUnits(), that.getCases());
  }

  public Count(CountDto model) {
    this(
        Double.parseDouble(model.getUnits()),
        Double.parseDouble(model.getCases())
    );
  }

  public static Count ofZero() {
    return ZERO;
  }

  public static Count countOf(String units, String cases) {
    return new Count(Double.parseDouble(units), Double.parseDouble(cases));
  }

  public static Count copy(Count other) {
    return new Count(other);
  }

  public Count add(Count other) {
    return new Count(
        Double.sum(this.units, other.units),
        Double.sum(this.cases, other.cases)
    );
  }

  public double getUnits() {
    return units;
  }

  private void setUnits(double count) {
    isGreaterThanZero(count);
    this.units = count;
  }

  public double getCases() {
    return cases;
  }

  private void setCases(double count) {
    isGreaterThanZero(count);
    this.cases = count;
  }

  private void isGreaterThanZero(double count) {
    if (Double.compare(count, 0.00) < 0) {
      throw new IllegalArgumentException(
          "Count cannot have a negative value, was supplied " + count);
    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Count that = (Count) o;
    return Double.compare(that.getUnits(), getUnits()) == 0
        && Double.compare(that.getCases(), getCases()) == 0;
  }

  @Override
  public int hashCode() {
    return Objects.hash(getUnits(), getCases());
  }

  @Override
  public String toString() {
    return "Count{"
        + "units=" + units
        + ", cases=" + cases
        + '}';
  }
}
