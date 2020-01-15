package com.gios.airindex.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Station {

    private long id;
    private long stationId;
    @JsonProperty(value = "stationName")
    private String name;
    private String gegrLat;
    private String gegrLon;
    private City city;
    @JsonProperty(value = "addressStreet")
    private String address;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getStationId() {
        return stationId;
    }

    public void setStationId(long stationId) {
        this.stationId = stationId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGegrLat() {
        return gegrLat;
    }

    public void setGegrLat(String gegrLat) {
        this.gegrLat = gegrLat;
    }

    public String getGegrLon() {
        return gegrLon;
    }

    public void setGegrLon(String gegrLon) {
        this.gegrLon = gegrLon;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Station{");
        sb.append("id=").append(id);
        sb.append(", stationId=").append(stationId);
        sb.append(", name='").append(name).append('\'');
        sb.append(", gegrLat='").append(gegrLat).append('\'');
        sb.append(", gegrLon='").append(gegrLon).append('\'');
        sb.append(", city=").append(city);
        sb.append(", address='").append(address).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
