package com.gios.airindex.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StIndexLevel {

    private long id;
    private String indexLevelName;


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("StIndexLevel{");
        sb.append("id=").append(id);
        sb.append(", indexLevelName='").append(indexLevelName).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getIndexLevelName() {
        return indexLevelName;
    }

    public void setIndexLevelName(String indexLevelName) {
        this.indexLevelName = indexLevelName;
    }
}
