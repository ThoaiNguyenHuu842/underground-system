# Introduction

This small project implements the **UndegroundSystem** service which contains 3 functions:
- _checkIn_: the function has 3 parameters:
  - id - the identifier of a person who is entering the metro
  - stationName - the name of the station where the person enters
  - t - integer representation of check-in time.
- _checkOut_: has the same parameters, but the second parameter is the name of the station where the
  person leaves the metro, and the third parameter is the check-out time. 
- _getAverageTime_: calculates the average transfer time between stations for all trips between two
  stations. It has two parameters:
  - the name of the starting station
  - the name of the final station. 
  
## Tech stack

JDK 11, Gradle, JUnit

## How to get started?

We can start this project using below gradle commands which will run an example of UndegroundSystem in `MainApplication.java` :

```shell
gradle build
gradle run
```
To run unit test
```shell
gradle build
gradle test
```