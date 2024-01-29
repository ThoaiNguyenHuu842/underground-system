package com.rikkeisoft.entity;

public class CheckInRecord {

  private Integer id;
  private String checkInStation;
  private String checkOutStation;
  private Integer checkInTime;
  private Integer checkOutTime;

  public CheckInRecord(Integer id, String checkInStation, Integer checkInTime) {
    this.id = id;
    this.checkInStation = checkInStation;
    this.checkInTime = checkInTime;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getCheckInStation() {
    return checkInStation;
  }

  public void setCheckInStation(String checkInStation) {
    this.checkInStation = checkInStation;
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

  public void setCheckInTime(Integer checkInTime) {
    this.checkInTime = checkInTime;
  }

  public Integer getCheckOutTime() {
    return checkOutTime;
  }

  public void setCheckOutTime(Integer checkOutTime) {
    this.checkOutTime = checkOutTime;
  }
}