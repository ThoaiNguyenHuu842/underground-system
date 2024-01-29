package com.rikkeisoft.entity;

import java.util.Objects;

public class Trip {
  private String startStation;
  private String endStation;

  public Trip(String startStation, String endStation) {
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
    Trip trip = (Trip) o;
    return startStation.equals(trip.startStation) && endStation.equals(trip.endStation);
  }

  @Override
  public int hashCode() {
    return Objects.hash(startStation, endStation);
  }
}
