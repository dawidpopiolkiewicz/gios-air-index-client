package com.gios.airindex.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Commune {

    private long id;
    private String communeName;
    private String districtName;
    private String provinceName;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCommuneName() {
        return communeName;
    }

    public void setCommuneName(String communeName) {
        this.communeName = communeName;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Commune{");
        sb.append("id=").append(id);
        sb.append(", communeName='").append(communeName).append('\'');
        sb.append(", districtName='").append(districtName).append('\'');
        sb.append(", provinceName='").append(provinceName).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
