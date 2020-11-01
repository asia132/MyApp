package com.example.myapplication.data;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Lesson implements Serializable, Cloneable {
	public Integer id;
	public String name;
	public Integer sourceLanguageid;
	public Integer destinationLanguageid;

	public Lesson() {
	}

	public Lesson(Integer id, String name, Integer sourceLanguageid, Integer destinationLanguageid) {
		this.id = id;
		this.name = name;
		this.sourceLanguageid = sourceLanguageid;
		this.destinationLanguageid = destinationLanguageid;
	}
	
	public boolean isPersisted() {
		return id != null;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Integer getSourceLanguageid() {
		return sourceLanguageid;
	}

	public void setSourceLanguageid(Integer sourceLanguageid) {
		this.sourceLanguageid = sourceLanguageid;
	}

	public Integer getDestinationLanguageid() {
		return destinationLanguageid;
	}

	public void setDestinationLanguageid(Integer destinationLanguageid) {
		this.destinationLanguageid = destinationLanguageid;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (this.id == null) {
			return false;
		}

		if (obj instanceof Lesson && obj.getClass().equals(getClass())) {
			return this.id.equals(((Lesson) obj).id);
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hash = 5;
		hash = 43 * hash + (id == null ? 0 : id.hashCode());
		return hash;
	}

	@Override
	public Lesson clone() throws CloneNotSupportedException {
		return (Lesson) super.clone();
	}

	@Override
	public String toString() {
		return name;
	}
}
