package com.kazuya.product.service.dto;

public class ProductDto {
  private long id;
  private String name;
  private long amount;

  public ProductDto(long id, String name, long amount) {
    this.id = id;
    this.name = name;
    this.amount = amount;
  }

  public long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public long getAmount() {
    return amount;
  }
}
