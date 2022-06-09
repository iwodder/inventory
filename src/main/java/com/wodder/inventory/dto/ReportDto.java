package com.wodder.inventory.dto;

import java.util.HashMap;
import java.util.Map;

public class ReportDto {
  private String generationDate;
  private String startDate;
  private String endDate;
  private Map<InventoryItemDto, UsageDto> usageDtoMap = new HashMap<>();

  public String getGenerationDate() {
    return generationDate;
  }

  public void setGenerationDate(String date) {
    this.generationDate = date;
  }

  public void setStartingDate(String startDate) {
    this.startDate = startDate;
  }

  public void setEndingDate(String endDate) {
    this.endDate = endDate;
  }

  public void addUsage(InventoryItemDto itemDto, UsageDto u) {
    usageDtoMap.putIfAbsent(itemDto, u);
  }

  public String getStartDate() {
    return startDate;
  }

  public String getEndDate() {
    return endDate;
  }

  public int getUsageCnt() {
    return usageDtoMap.size();
  }
}
