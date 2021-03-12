package com.ufranco.checkDolarPrice.models;

import java.util.Objects;

public class Currency {
  private String name;
  private Double buyValue;
  private Double sellValue;

  public Currency() {}


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Double getBuyValue() {
    return buyValue;
  }

  public void setBuyValue(Double buyValue) {
    this.buyValue = buyValue;
  }

  public Double getSellValue() {
    return sellValue;
  }

  public void setSellValue(Double sellValue) {
    this.sellValue = sellValue;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Currency currency = (Currency) o;
    return name.equals(currency.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name);
  }

  @Override
  public String toString() {
    return "Currency{" +
      "name='" + name + '\'' +
      ", buyValue=" + buyValue +
      ", sellValue=" + sellValue +
      '}';
  }
}
