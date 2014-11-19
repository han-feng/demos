package org.demos.drools.cep;

import java.util.List;

public class Subject {

	private String id;
	private SubjectType subjectType;
	private Subject parent;
	private List<Subject> child;

	public List<Subject> getChild() {
		return child;
	}

	public void setChild(List<Subject> child) {
		this.child = child;
	}

	public Subject getParent() {
		return parent;
	}

	public void setParent(Subject parent) {
		this.parent = parent;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public SubjectType getSubjectType() {
		return subjectType;
	}

	public void setSubjectType(SubjectType subjectType) {
		this.subjectType = subjectType;
	}

	public String toString() {
		return "Subject(id:\"" + id + "\")";
	}

}
