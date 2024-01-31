package com.rikkeisoft.service;

import com.rikkeisoft.entity.CheckInRecord;
import com.rikkeisoft.entity.Route;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UndergroundSystem {

  private final List<Route> routes;
  private Map<Integer, CheckInRecord> mapPersonalIdAndCurrentCheckIn = new HashMap<>();
  private Map<Route, Double> mapRouteAndAverageTime = new HashMap<>();
  private Map<Route, Integer> mapRouteAndTotalCheckIns = new HashMap<>();

  public UndergroundSystem(List<Route> routes) {
    this.routes = routes;
  }

  public void checkIn(int id, String stationName, int t) {
    validateInput(id, stationName, t);
    validateStartStation(stationName);
    if (t <= 0) {
      throw new IllegalArgumentException(String.format("Check-in time %d is invalid", t));
    }

    boolean hasExistingCheckInRecord = mapPersonalIdAndCurrentCheckIn.containsKey(id);
    if (hasExistingCheckInRecord) {
      throw new IllegalArgumentException(String.format("Don't allow to check in because personal id %d checked in already but hasn't checked out", id));
    }

    mapPersonalIdAndCurrentCheckIn.put(id, new CheckInRecord(stationName, t));
  }

  public void checkOut(int id, String stationName, int t) {
    validateInput(id, stationName, t);
    validateEndStation(stationName);
    if (t <= 0) {
      throw new IllegalArgumentException(String.format("Check-out time %d is invalid", t));
    }

    if (!mapPersonalIdAndCurrentCheckIn.containsKey(id)) {
      throw new IllegalArgumentException(String.format("Personal id %d hasn't checked in yet", id));
    }

    CheckInRecord currentCheckInRecord = mapPersonalIdAndCurrentCheckIn.get(id);
    if (routes.stream().noneMatch(
      route -> route.getStartStation().equals(currentCheckInRecord.getCheckInStation()) && route.getEndStation().equals(stationName))) {
      throw new IllegalArgumentException(String.format("Don't have any trip from station %s to station %s", currentCheckInRecord.getCheckInStation(), stationName));
    }
    if (t <= currentCheckInRecord.getCheckInTime()) {
      throw new IllegalArgumentException(String.format("Check-out time %d is less than check-in time %d", t, currentCheckInRecord.getCheckInTime()));
    }

    currentCheckInRecord.setCheckOutStation(stationName);
    currentCheckInRecord.setCheckOutTime(t);

    populateMapRouteAverageTime(currentCheckInRecord);
    mapPersonalIdAndCurrentCheckIn.remove(id);
  }

  public double getAverageTime(String startStation, String endStation) {
    validateStartStation(startStation);
    validateEndStation(endStation);
    return mapRouteAndAverageTime.getOrDefault(new Route(startStation, endStation), 0d);
  }

  private void validateInput(int id, String stationName, int t) {
    if (id <= 0) {
      throw new IllegalArgumentException(String.format("Personal id %d is invalid", id));
    }

    if (stationName == null) {
      throw new IllegalArgumentException("Station name must not be empty");
    }
  }

  private void validateStartStation(String startStation) {
    if (routes.stream().noneMatch(t -> t.getStartStation().equals(startStation))) {
      throw new IllegalArgumentException(String.format("Start station name %s does not exists", startStation));
    }
  }

  private void validateEndStation(String endStation) {
    if (routes.stream().noneMatch(t -> t.getEndStation().equals(endStation))) {
      throw new IllegalArgumentException(String.format("End station name %s does not exists", endStation));
    }
  }

  private void populateMapRouteAverageTime(CheckInRecord currentCheckInRecord) {
    Route route = new Route(currentCheckInRecord.getCheckInStation(), currentCheckInRecord.getCheckOutStation());
    Integer previousTotalCheckIns =  mapRouteAndTotalCheckIns.getOrDefault(route,0);
    Integer currentTotalCheckIns = previousTotalCheckIns + 1;

    int time = currentCheckInRecord.getCheckOutTime() - currentCheckInRecord.getCheckInTime();
    if (!mapRouteAndAverageTime.containsKey(route)) {
      mapRouteAndAverageTime.put(route, Double.valueOf(time));
    } else {
      Double currentAverageTime = mapRouteAndAverageTime.get(route);
      mapRouteAndAverageTime.put(route, ((currentAverageTime * previousTotalCheckIns) + time) / currentTotalCheckIns);
    }

    mapRouteAndTotalCheckIns.put(route, currentTotalCheckIns);
  }
}
