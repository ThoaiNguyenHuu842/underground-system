package com.rikkeisoft.entity;

import java.util.Objects;

public class Route {
  private String startStation;
  private String endStation;

  public Route(String startStation, String endStation) {
    this.startStation = startStation;
    this.endStation = endStation;
  }

  public String getStartStation() {
    return startStation;
  }

  public String getEndStation() {
    return endStation;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Route route = (Route) o;
    return startStation.equals(route.startStation) && endStation.equals(route.endStation);
  }

  @Override
  public int hashCode() {
    return Objects.hash(startStation, endStation);
  }
}
