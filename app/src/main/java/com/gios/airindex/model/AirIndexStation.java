package com.gios.airindex.model;

public class AirIndexStation {

    private long id;
    private long stationId;
    private String stationName;
    private String city;
    private String address;
    private String calcDate;
    private String indexLevelName;

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

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCalcDate() {
        return calcDate;
    }

    public void setCalcDate(String calcDate) {
        this.calcDate = calcDate;
    }

    public String getIndexLevelName() {
        return indexLevelName;
    }

    public void setIndexLevelName(String indexLevelName) {
        this.indexLevelName = indexLevelName;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AirIndexStation{");
        sb.append("id=").append(id);
        sb.append(", stationId=").append(stationId);
        sb.append(", stationName='").append(stationName).append('\'');
        sb.append(", city='").append(city).append('\'');
        sb.append(", address='").append(address).append('\'');
        sb.append(", calcDate='").append(calcDate).append('\'');
        sb.append(", indexLevelName='").append(indexLevelName).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
