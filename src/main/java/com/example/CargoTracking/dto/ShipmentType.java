package com.example.CargoTracking.dto;

public enum ShipmentType {
    BY_AIR("By Air"),
    BY_ROAD("By Road");

    private final String type;

    ShipmentType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}