package org.demos.drools.cep;

import java.util.Map;

public class EventFact {

	private String subjectId;
	private String subjectTypeId;
	private Map<String, Object> indexMap;

	public Map<String, Object> getIndexMap() {
		return indexMap;
	}

	public void setIndexMap(Map<String, Object> indexMap) {
		this.indexMap = indexMap;
	}

	public String getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}

	public String getSubjectTypeId() {
		return subjectTypeId;
	}

	public void setSubjectTypeId(String subjectTypeId) {
		this.subjectTypeId = subjectTypeId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public long getExpires() {
		return expires;
	}

	public void setExpires(long expires) {
		this.expires = expires;
	}

	private String name;
	private Object value;
	private long time;
	private long expires;

	public String toString() {
		return "EventFact(subjectId:\"" + subjectId + "\", subjectTypeId:\""
				+ subjectTypeId + "\", name:\"" + name + "\", value:\"" + value
				+ "\")";
	}
}
