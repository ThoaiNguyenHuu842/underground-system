package com.rikkeisoft.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.rikkeisoft.entity.Trip;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

public class UndergroundSystemTest {

  @Test
  public void checkInSuccess() {
    List<Trip> trips = new ArrayList<>();
    trips.add(new Trip("A", "B"));
    UndergroundSystem undergroundSystem = new UndergroundSystem(trips);
    assertDoesNotThrow(() ->  undergroundSystem.checkIn(23, "A", 20));
  }

  @Test
  public void checkInError_whenPersonalIdIsInvaild() {
    List<Trip> trips = new ArrayList<>();
    trips.add(new Trip("A", "B"));
    UndergroundSystem undergroundSystem = new UndergroundSystem(trips);
    Throwable exception = assertThrows(IllegalArgumentException.class,
      () ->  undergroundSystem.checkIn(-23, "A", 10));
    assertEquals("Personal id -23 is invalid", exception.getMessage());
  }

  @Test
  public void checkInError_whenStationNameIsEmpty() {
    List<Trip> trips = new ArrayList<>();
    trips.add(new Trip("A", "B"));
    UndergroundSystem undergroundSystem = new UndergroundSystem(trips);
    Throwable exception = assertThrows(IllegalArgumentException.class,
      () ->  undergroundSystem.checkIn(1, null, 10));
    assertEquals("Station name must not be empty", exception.getMessage());
  }

  @Test
  public void checkInError_whenCheckInTimeIsInvaild() {
    List<Trip> trips = new ArrayList<>();
    trips.add(new Trip("A", "B"));
    UndergroundSystem undergroundSystem = new UndergroundSystem(trips);
    Throwable exception = assertThrows(IllegalArgumentException.class,
      () ->  undergroundSystem.checkIn(23, "A", -10));
    assertEquals("Check-in time -10 is invalid", exception.getMessage());
  }

  @Test
  public void checkInError_whenStartStationDoesNotExists() {
    List<Trip> trips = new ArrayList<>();
    trips.add(new Trip("A", "B"));
    UndergroundSystem undergroundSystem = new UndergroundSystem(trips);
    Throwable exception = assertThrows(IllegalArgumentException.class,
      () ->  undergroundSystem.checkIn(23, "AA", 10));
    assertEquals("Start station name AA does not exists", exception.getMessage());
  }

  @Test
  public void checkInError_whenHasCheckedInAlready() {
    List<Trip> trips = new ArrayList<>();
    trips.add(new Trip("A", "B"));
    UndergroundSystem undergroundSystem = new UndergroundSystem(trips);
    undergroundSystem.checkIn(23, "A", 10);
    Throwable exception = assertThrows(IllegalArgumentException.class,
      () ->  undergroundSystem.checkIn(23, "A", 100));
    assertEquals("Don't allow to check in because personal id 23 checked in already but hasn't checked out", exception.getMessage());
  }

  @Test
  public void checkOutError_whenEndStationDoesNotExists() {
    List<Trip> trips = new ArrayList<>();
    trips.add(new Trip("A", "B"));
    UndergroundSystem undergroundSystem = new UndergroundSystem(trips);
    Throwable exception = assertThrows(IllegalArgumentException.class,
      () ->  undergroundSystem.checkOut(23, "BB", 10));
    assertEquals("End station name BB does not exists", exception.getMessage());
  }

  @Test
  public void checkOutError_whenCheckOutTimeIsInvaild() {
    List<Trip> trips = new ArrayList<>();
    trips.add(new Trip("A", "B"));
    UndergroundSystem undergroundSystem = new UndergroundSystem(trips);
    Throwable exception = assertThrows(IllegalArgumentException.class,
      () ->  undergroundSystem.checkOut(23, "B", -10));
    assertEquals("Check-out time -10 is invalid", exception.getMessage());
  }

  @Test
  public void checkOutError_whenHasNotCheckedInYet() {
    List<Trip> trips = new ArrayList<>();
    trips.add(new Trip("A", "B"));
    UndergroundSystem undergroundSystem = new UndergroundSystem(trips);
    Throwable exception = assertThrows(IllegalArgumentException.class,
      () ->  undergroundSystem.checkOut(23, "B", 100));
    assertEquals("Personal id 23 hasn't checked in yet", exception.getMessage());
  }

  @Test
  public void checkOutError_whenCheckOutTimeIsLessThanCheckInTime() {
    List<Trip> trips = new ArrayList<>();
    trips.add(new Trip("A", "B"));
    UndergroundSystem undergroundSystem = new UndergroundSystem(trips);
    undergroundSystem.checkIn(23, "A", 100);
    Throwable exception = assertThrows(IllegalArgumentException.class,
      () ->  undergroundSystem.checkOut(23, "B", 40));
    assertEquals("Check-out time 40 is less than check-in time 100", exception.getMessage());
  }

  @Test
  public void checkOutError_whenNoTripMatched() {
    List<Trip> trips = new ArrayList<>();
    trips.add(new Trip("A", "B"));
    trips.add(new Trip("B", "C"));
    UndergroundSystem undergroundSystem = new UndergroundSystem(trips);
    undergroundSystem.checkIn(23, "A", 100);
    Throwable exception = assertThrows(IllegalArgumentException.class,
      () ->  undergroundSystem.checkOut(23, "C", 40));
    assertEquals("Don't have any trip from station A to station C", exception.getMessage());
  }

  @Test
  public void checkOutSuccess() {
    List<Trip> trips = new ArrayList<>();
    trips.add(new Trip("A", "B"));
    UndergroundSystem undergroundSystem = new UndergroundSystem(trips);
    undergroundSystem.checkIn(23, "A", 20);
    assertDoesNotThrow(() ->  undergroundSystem.checkOut(23, "B", 120));
  }

  @Test
  public void getAverageTimeSuccess() {
    List<Trip> trips = new ArrayList<>();
    trips.add(new Trip("A", "B"));
    trips.add(new Trip("B", "C"));
    trips.add(new Trip("A", "C"));
    trips.add(new Trip("D", "C"));
    UndergroundSystem undergroundSystem = new UndergroundSystem(trips);

    undergroundSystem.checkIn(1, "A", 40);
    undergroundSystem.checkOut(1, "C", 100);
    undergroundSystem.checkIn(2, "A", 10);
    undergroundSystem.checkOut(2, "C", 20);
    undergroundSystem.checkIn(3, "D", 15);
    undergroundSystem.checkOut(3, "C", 80);
    undergroundSystem.checkIn(12, "A", 10);
    undergroundSystem.checkOut(12, "C", 90);
    assertEquals(50, undergroundSystem.getAverageTime("A", "C"));
  }
}
