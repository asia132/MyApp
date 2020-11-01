package com.example.myapplication.data;

import java.io.Serializable;

@SuppressWarnings("serial")
public class User implements Serializable, Cloneable {
	public Integer id;
	public String name;
	public Integer currentSourceLanguageId;
	public Integer currentDestinationLanguageId;

	public User() {

	}

	public User(Integer id, String name, Integer currentSourceLanguageId, Integer currentDestinationLanguageId) {
		super();
		this.id = id;
		this.name = name;
		this.currentSourceLanguageId = currentSourceLanguageId;
		this.currentDestinationLanguageId = currentDestinationLanguageId;
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

	public Integer getCurrentSourceLanguageId() {
		return currentSourceLanguageId;
	}

	public void setCurrentSourceLanguageId(Integer currentSourceLanguageId) {
		this.currentSourceLanguageId = currentSourceLanguageId;
	}

	public Integer getCurrentDestinationLanguageId() {
		return currentDestinationLanguageId;
	}

	public void setCurrentDestinationLanguageId(Integer currentDestinationLanguageId) {
		this.currentDestinationLanguageId = currentDestinationLanguageId;
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

		if (obj instanceof User && obj.getClass().equals(getClass())) {
			return this.id.equals(((User) obj).id);
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
	public User clone() throws CloneNotSupportedException {
		return (User) super.clone();
	}

	@Override
	public String toString() {
		return name;
	}
}
