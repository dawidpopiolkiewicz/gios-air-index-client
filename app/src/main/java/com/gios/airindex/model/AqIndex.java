package com.gios.airindex.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(value = {"stSourceDataDate","so2CalcDate","so2IndexLevel","so2SourceDataDate",
        "no2CalcDate","no2IndexLevel","no2SourceDataDate","coCalcDate","coIndexLevel",
        "coSourceDataDate","pm10CalcDate","pm10IndexLevel","pm10SourceDataDate","pm25CalcDate",
        "pm25IndexLevel","pm25SourceDataDate","o3CalcDate","o3IndexLevel","o3SourceDataDate",
        "c6h6CalcDate","c6h6IndexLevel","c6h6SourceDataDate","stIndexStatus","stIndexCrParam"})
public class AqIndex {

    private long id;
    @JsonProperty(value = "stCalcDate")
    private String calcDate;
    private StIndexLevel stIndexLevel;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AqIndex{");
        sb.append("id=").append(id);
        sb.append(", calcDate='").append(calcDate).append('\'');
        sb.append(", stIndexLevel=").append(stIndexLevel);
        sb.append('}');
        return sb.toString();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCalcDate() {
        return calcDate;
    }

    public void setCalcDate(String calcDate) {
        this.calcDate = calcDate;
    }

    public StIndexLevel getStIndexLevel() {
        return stIndexLevel;
    }

    public void setStIndexLevel(StIndexLevel stIndexLevel) {
        this.stIndexLevel = stIndexLevel;
    }
}
