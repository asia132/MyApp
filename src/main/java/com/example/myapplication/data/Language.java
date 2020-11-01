package com.example.myapplication.data;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Language implements Serializable, Cloneable {
	public Integer id;
	public String name;

	public Language() {
	}

	public Language(Integer id, String name) {
		this.id = id;
		this.name = name;
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
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (this.id == null) {
			return false;
		}

		if (obj instanceof Language && obj.getClass().equals(getClass())) {
			return this.id.equals(((Language) obj).id);
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
	public Language clone() throws CloneNotSupportedException {
		return (Language) super.clone();
	}

	@Override
	public String toString() {
		return name;
	}
}
