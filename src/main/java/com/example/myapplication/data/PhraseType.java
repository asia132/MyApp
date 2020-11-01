package com.example.myapplication.data;

public enum PhraseType {
	WORD("W", "Word"), STATEMENT("S", "Statement");

	private String name;

	private String id;

	PhraseType(String id, String name) {
		this.id = id;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public String getId() {
		return id;
	}
}
