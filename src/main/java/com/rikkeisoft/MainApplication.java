package com.rikkeisoft;

import com.rikkeisoft.entity.Route;
import com.rikkeisoft.service.UndergroundSystem;
import java.util.ArrayList;
import java.util.List;

public class MainApplication {

  public static void main(String[] args) {
    List<Route> routes = new ArrayList<>();
    routes.add(new Route("A", "B"));
    routes.add(new Route("B", "C"));
    routes.add(new Route("A", "C"));
    UndergroundSystem undergroundSystem = new UndergroundSystem(routes);

    undergroundSystem.checkIn(23, "A", 20);
    undergroundSystem.checkOut(23, "C", 30);
    undergroundSystem.checkIn(25, "A", 60);
    undergroundSystem.checkOut(25, "C", 70);
    undergroundSystem.checkIn(19, "A", 15);
    undergroundSystem.checkOut(19, "B", 80);

    System.out.println(String.format("the average time between stations A and C is %.2f", undergroundSystem.getAverageTime("A", "C")));
  }
}
