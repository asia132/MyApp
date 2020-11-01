package com.example.myapplication.service;

import com.example.myapplication.data.User;

public class UserService {
	private static UserService instance;
	private final LanguageService languageService = LanguageService.getInstance();
	private final User user = new User();

	/**
	 * @return a reference to an example facade for Customer objects.
	 */
	public static UserService getInstance() {
		if (instance == null) {
			instance = new UserService();
			instance.ensureTestData();
		}
		return instance;
	}

	public String getCurrentUserName() {
		return user.getName();
	}
	
	public String getCurrentDestinationLanguageName() {
		return languageService.getLanguageNameOfIndex(user.getCurrentDestinationLanguageId());
	}
	
	public Integer getCurrentDestinationLanguageId() {
		return user.getCurrentDestinationLanguageId();
	}
	
	public String getCurrentSourceLanguageName() {
		return languageService.getLanguageNameOfIndex(user.getCurrentSourceLanguageId());
	}
	
	public Integer getCurrentSourceLanguageId() {
		return user.getCurrentSourceLanguageId();
	}

	/**
	 * Sample data generation
	 */
	public void ensureTestData() {
		user.setId(1);
		user.setName("Domyślny użytkownik");
		user.setCurrentDestinationLanguageId(languageService.count() - 2);
		user.setCurrentSourceLanguageId(languageService.count() - 1);
	}
}
