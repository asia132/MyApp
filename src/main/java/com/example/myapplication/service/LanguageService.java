package com.example.myapplication.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import com.example.myapplication.data.Language;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LanguageService {
	private static LanguageService instance;
	private static final Logger LOGGER = Logger.getLogger(LanguageService.class.getName());

	private final HashMap<Integer, Language> languages = new HashMap<>();
	private int nextId = 0;

	private LanguageService() {
	}

	/**
	 * @return a reference to an example facade for Customer objects.
	 */
	public static LanguageService getInstance() {
		if (instance == null) {
			instance = new LanguageService();
			instance.ensureTestData();
		}
		return instance;
	}

	/**
	 * @return all available Customer objects.
	 */
	public synchronized List<Language> findAll() {
		return findAll(null);
	}

	/**
	 * Finds all Customer's that match given filter.
	 *
	 * @param stringFilter
	 *            filter that returned objects should match or null/empty string
	 *            if all objects should be returned.
	 * @return list a Customer objects
	 */
	public synchronized List<Language> findAll(String stringFilter) {
		return findAll(stringFilter, 0, languages.size());
	}

	/**
	 * Finds all Customer's that match given filter and limits the resultset.
	 *
	 * @param stringFilter
	 *            filter that returned objects should match or null/empty string
	 *            if all objects should be returned.
	 * @param start
	 *            the index of first result
	 * @param maxresults
	 *            maximum result count
	 * @return list a Customer objects
	 */
	public synchronized List<Language> findAll(String stringFilter, int start, int maxresults) {
		ArrayList<Language> arrayList = new ArrayList<>();
		for (Language language : languages.values()) {
			try {
				boolean passesFilter = (stringFilter == null || stringFilter.isEmpty())
						|| language.toString().toLowerCase().contains(stringFilter.toLowerCase());
				if (passesFilter) {
					arrayList.add(language.clone());
				}
			} catch (CloneNotSupportedException ex) {
				Logger.getLogger(LanguageService.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		Collections.sort(arrayList, new Comparator<Language>() {

			@Override
			public int compare(Language o1, Language o2) {
				return (int) (o2.getId() - o1.getId());
			}
		});
		int end = start + maxresults;
		if (end > arrayList.size()) {
			end = arrayList.size();
		}
		return arrayList.subList(start, end);
	}

	/**
	 * @return the amount of all customers in the system
	 */
	public synchronized int count() {
		return languages.size();
	}
	
	public String getLanguageNameOfIndex(Integer index)
	{
		return languages.get(index).getName();
	}

	/**
	 * Deletes a customer from a system
	 *
	 * @param value
	 *            the Customer to be deleted
	 */
	public synchronized void delete(Language value) {
		languages.remove(value.getId());
	}

	/**
	 * Persists or updates customer in the system. Also assigns an identifier
	 * for new Customer instances.
	 *
	 * @param entry
	 */
	public synchronized void save(Language entry) {
		if (entry == null) {
			LOGGER.log(Level.SEVERE,
					"Customer is null. Are you sure you have connected your form to the application as described in tutorial chapter 7?");
			return;
		}
		if (entry.getId() == null) {
			entry.setId(nextId++);
		}
		languages.put(entry.getId(), entry);
	}

	/**
	 * Sample data generation
	 */
	public void ensureTestData() {
		if (findAll().isEmpty()) {
			final String[] names = new String[] { "Francuski", "Hiszpański", "Angielski", "Polski" };
			for (String name : names) {
				Language c = new Language();
				c.setName(name);
				save(c);
			}
		}
	}
}
