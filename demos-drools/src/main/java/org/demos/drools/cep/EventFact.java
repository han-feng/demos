package org.demos.drools.cep;

import java.util.Map;

public class EventFact {

    private long expires;
    private Map<String, Object> indexMap;
    private String name;
    private String subjectId;
    private String subjectTypeId;
    private long time;
    private Object value;

    public long getExpires() {
        return expires;
    }

    public Map<String, Object> getIndexMap() {
        return indexMap;
    }

    public String getName() {
        return name;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public String getSubjectTypeId() {
        return subjectTypeId;
    }

    public long getTime() {
        return time;
    }

    public Object getValue() {
        return value;
    }

    public void setExpires(long expires) {
        this.expires = expires;
    }

    public void setIndexMap(Map<String, Object> indexMap) {
        this.indexMap = indexMap;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public void setSubjectTypeId(String subjectTypeId) {
        this.subjectTypeId = subjectTypeId;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String toString() {
        return "EventFact(subjectId:\"" + subjectId + "\", subjectTypeId:\""
                + subjectTypeId + "\", name:\"" + name + "\", value:\"" + value
                + "\")";
    }
}
