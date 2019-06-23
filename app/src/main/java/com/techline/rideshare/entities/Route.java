package com.techline.rideshare.entities;

public class Route {
    private String routeId;
    private String pickUpPlaceId;
    private String pickUpGeometry;
    private String pickUpLocation_type;
    private String pickUpLocation;
    private String pickUpLat;
    private String pickUpLng;
    private String whereToPlaceId;
    private String whereToGeometry;
    private String whereToLocation_type;
    private String whereToLat;
    private String whereToLocation;
    private String whereToLng;
    private String accountNumber;

    public Route() {
    }

    public Route(String routeId, String pickUpPlaceId, String pickUpGeometry, String pickUpLocation_type,
                 String pickUpLocation, String pickUpLat, String pickUpLng, String whereToPlaceId,
                 String whereToGeometry, String whereToLocation_type, String whereToLat, String whereToLocation,
                 String whereToLng, String accountNumber) {
        this.routeId = routeId;
        this.pickUpPlaceId = pickUpPlaceId;
        this.pickUpGeometry = pickUpGeometry;
        this.pickUpLocation_type = pickUpLocation_type;
        this.pickUpLocation = pickUpLocation;
        this.pickUpLat = pickUpLat;
        this.pickUpLng = pickUpLng;
        this.whereToPlaceId = whereToPlaceId;
        this.whereToGeometry = whereToGeometry;
        this.whereToLocation_type = whereToLocation_type;
        this.whereToLat = whereToLat;
        this.whereToLocation = whereToLocation;
        this.whereToLng = whereToLng;
        this.accountNumber = accountNumber;
    }

    public String getRouteId() {
        return routeId;
    }

    public void setRouteId(String routeId) {
        this.routeId = routeId;
    }

    public String getPickUpPlaceId() {
        return pickUpPlaceId;
    }

    public void setPickUpPlaceId(String pickUpPlaceId) {
        this.pickUpPlaceId = pickUpPlaceId;
    }

    public String getPickUpGeometry() {
        return pickUpGeometry;
    }

    public void setPickUpGeometry(String pickUpGeometry) {
        this.pickUpGeometry = pickUpGeometry;
    }

    public String getPickUpLocation_type() {
        return pickUpLocation_type;
    }

    public void setPickUpLocation_type(String pickUpLocation_type) {
        this.pickUpLocation_type = pickUpLocation_type;
    }

    public String getPickUpLocation() {
        return pickUpLocation;
    }

    public void setPickUpLocation(String pickUpLocation) {
        this.pickUpLocation = pickUpLocation;
    }

    public String getPickUpLat() {
        return pickUpLat;
    }

    public void setPickUpLat(String pickUpLat) {
        this.pickUpLat = pickUpLat;
    }

    public String getPickUpLng() {
        return pickUpLng;
    }

    public void setPickUpLng(String pickUpLng) {
        this.pickUpLng = pickUpLng;
    }

    public String getWhereToPlaceId() {
        return whereToPlaceId;
    }

    public void setWhereToPlaceId(String whereToPlaceId) {
        this.whereToPlaceId = whereToPlaceId;
    }

    public String getWhereToGeometry() {
        return whereToGeometry;
    }

    public void setWhereToGeometry(String whereToGeometry) {
        this.whereToGeometry = whereToGeometry;
    }

    public String getWhereToLocation_type() {
        return whereToLocation_type;
    }

    public void setWhereToLocation_type(String whereToLocation_type) {
        this.whereToLocation_type = whereToLocation_type;
    }

    public String getWhereToLat() {
        return whereToLat;
    }

    public void setWhereToLat(String whereToLat) {
        this.whereToLat = whereToLat;
    }

    public String getWhereToLocation() {
        return whereToLocation;
    }

    public void setWhereToLocation(String whereToLocation) {
        this.whereToLocation = whereToLocation;
    }

    public String getWhereToLng() {
        return whereToLng;
    }

    public void setWhereToLng(String whereToLng) {
        this.whereToLng = whereToLng;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
}
