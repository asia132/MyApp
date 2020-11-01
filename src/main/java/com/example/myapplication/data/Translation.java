package com.example.myapplication.data;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Translation implements Serializable, Cloneable {
	public Integer id;
	public String sourceText;
	public String destinationText;
	public String type;
	public int lessonId;

	public Translation(int lessonId) {
		super();
		this.lessonId = lessonId;
	}

	public Translation(Integer id, String sourceText, String destinationText, int lessonId,  String type) {
		super();
		this.id = id;
		this.sourceText = sourceText;
		this.destinationText = destinationText;
		this.type = type;
		this.lessonId = lessonId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSourceText() {
		return sourceText;
	}

	public void setSourceText(String sourceText) {
		this.sourceText = sourceText;
	}

	public String getDestinationText() {
		return destinationText;
	}

	public void setDestinationText(String destinationText) {
		this.destinationText = destinationText;
	}


	public void setType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public int getLessonId() {
		return lessonId;
	}

	public void setLessonId(int lessonId) {
		this.lessonId = lessonId;
	}
	
	public boolean isPersisted() {
		return id != null;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (this.id == null) {
			return false;
		}

		if (obj instanceof Translation && obj.getClass().equals(getClass())) {
			return this.id.equals(((Translation) obj).id);
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
	public Translation clone() throws CloneNotSupportedException {
		return (Translation) super.clone();
	}

	@Override
	public String toString() {
		return sourceText + " " + destinationText;
	}
}
