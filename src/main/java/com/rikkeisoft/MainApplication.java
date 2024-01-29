package com.rikkeisoft;

import com.rikkeisoft.entity.Trip;
import com.rikkeisoft.service.UndergroundSystem;
import java.util.ArrayList;
import java.util.List;

public class MainApplication {

  public static void main(String[] args) {
    List<Trip> trips = new ArrayList<>();
    trips.add(new Trip("A", "B"));
    trips.add(new Trip("B", "C"));
    trips.add(new Trip("A", "C"));
    UndergroundSystem undergroundSystem = new UndergroundSystem(trips);

    undergroundSystem.checkIn(23, "A", 20);
    undergroundSystem.checkOut(23, "C", 30);
    undergroundSystem.checkIn(25, "A", 60);
    undergroundSystem.checkOut(25, "C", 70);
    undergroundSystem.checkIn(19, "A", 15);
    undergroundSystem.checkOut(19, "B", 80);

    System.out.println(String.format("the average time between stations A and C is %.2f", undergroundSystem.getAverageTime("A", "C")));
  }
}
