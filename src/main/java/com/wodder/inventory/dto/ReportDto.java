package com.wodder.inventory.dto;

import java.time.LocalDate;

public class ReportDto {
  private LocalDate generationDate;

  public LocalDate getGenerationDate() {
    return generationDate;
  }

  public void setGenerationDate(LocalDate date) {
    this.generationDate = date;
  }
}
