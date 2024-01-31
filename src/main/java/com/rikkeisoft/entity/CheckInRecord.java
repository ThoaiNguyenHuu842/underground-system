package com.rikkeisoft.entity;

public class CheckInRecord {
  private String checkInStation;
  private String checkOutStation;
  private Integer checkInTime;
  private Integer checkOutTime;

  public CheckInRecord(String checkInStation, Integer checkInTime) {
    this.checkInStation = checkInStation;
    this.checkInTime = checkInTime;
  }


  public String getCheckInStation() {
    return checkInStation;
  }

  public String getCheckOutStation() {
    return checkOutStation;
  }

  public void setCheckOutStation(String checkOutStation) {
    this.checkOutStation = checkOutStation;
  }

  public Integer getCheckInTime() {
    return checkInTime;
  }

  public Integer getCheckOutTime() {
    return checkOutTime;
  }

  public void setCheckOutTime(Integer checkOutTime) {
    this.checkOutTime = checkOutTime;
  }
}
