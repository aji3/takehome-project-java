package com.kazuya.payment.dto;

import java.util.List;

public class CreatePaymentIntentRequest {

  private List<Item> items;

  public CreatePaymentIntentRequest() {}

  public CreatePaymentIntentRequest(List<Item> items) {
    this.items = items;
  }

  public List<Item> getItems() {
    return items;
  }

  public static class Item {
    private long itemId;
    private int count;

    public Item() {}

    public Item(long itemId, int count) {
      this.itemId = itemId;
      this.count = count;
    }

    public long getItemId() {
      return itemId;
    }

    public int getCount() {
      return count;
    }
  }
}
