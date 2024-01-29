package com.rikkeisoft.service;

import com.rikkeisoft.entity.CheckInRecord;
import com.rikkeisoft.entity.Trip;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class UndergroundSystem {

  private final List<Trip> trips;
  private ArrayList<CheckInRecord> checkInRecords = new ArrayList<>();
  private Map<Trip, Double> mapRouteAverageTime = new HashMap<>();

  public UndergroundSystem(List<Trip> trips) {
    this.trips = trips;
  }

  public void checkIn(int id, String stationName, int t) {
    validateInput(id, stationName, t);
    validateStartStation(stationName);
    if (t <= 0) {
      throw new IllegalArgumentException(String.format("Check-in time %d is invalid", t));
    }

    boolean hasExistingCheckInRecord = checkInRecords.stream().anyMatch(r -> r.getId() == id && r.getCheckOutTime() == null);
    if (hasExistingCheckInRecord) {
      throw new IllegalArgumentException(String.format("Don't allow to check in because personal id %d checked in already but hasn't checked out", id));
    }

    checkInRecords.add(new CheckInRecord(id, stationName, t));
  }

  public void checkOut(int id, String stationName, int t) {
    validateInput(id, stationName, t);
    validateEndStation(stationName);
    if (t <= 0) {
      throw new IllegalArgumentException(String.format("Check-out time %d is invalid", t));
    }

    Optional<CheckInRecord> existingCheckInRecordOptional = checkInRecords.stream().filter(r -> r.getId() == id && r.getCheckOutTime() == null).findFirst();
    if (existingCheckInRecordOptional.isEmpty()) {
      throw new IllegalArgumentException(String.format("Personal id %d hasn't checked in yet", id));
    }

    CheckInRecord existingCheckInRecord = existingCheckInRecordOptional.get();
    if (trips.stream().noneMatch(trip -> trip.getStartStation().equals(existingCheckInRecord.getCheckInStation()) && trip.getEndStation().equals(stationName))) {
      throw new IllegalArgumentException(String.format("Don't have any trip from station %s to station %s", existingCheckInRecord.getCheckInStation(), stationName));
    }
    if (t <= existingCheckInRecord.getCheckInTime()) {
      throw new IllegalArgumentException(String.format("Check-out time %d is less than check-in time %d", t, existingCheckInRecord.getCheckInTime()));
    }

    existingCheckInRecord.setCheckOutStation(stationName);
    existingCheckInRecord.setCheckOutTime(t);

    populateMapRouteAverageTime(existingCheckInRecord.getCheckInStation(), existingCheckInRecord.getCheckOutStation());
  }

  public double getAverageTime(String startStation, String endStation) {
    validateStartStation(startStation);
    validateEndStation(endStation);
    return mapRouteAverageTime.getOrDefault(new Trip(startStation, endStation), 0d);
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
    if (trips.stream().noneMatch(t -> t.getStartStation().equals(startStation))) {
      throw new IllegalArgumentException(String.format("Start station name %s does not exists", startStation));
    }
  }

  private void validateEndStation(String endStation) {
    if (trips.stream().noneMatch(t -> t.getEndStation().equals(endStation))) {
      throw new IllegalArgumentException(String.format("End station name %s does not exists", endStation));
    }
  }

  private void populateMapRouteAverageTime(String startStation, String endStation) {
    Double averageTime = checkInRecords.stream().filter(r -> startStation.equals(r.getCheckInStation()) &&
      endStation.equals(r.getCheckOutStation())).mapToInt(r -> r.getCheckOutTime() - r.getCheckInTime()).average().getAsDouble();
    mapRouteAverageTime.put(new Trip(startStation, endStation), averageTime);
  }
}
