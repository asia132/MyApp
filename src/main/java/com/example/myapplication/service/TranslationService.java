package com.example.myapplication.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.example.myapplication.data.PhraseType;
import com.example.myapplication.data.Translation;

public class TranslationService {
	private static TranslationService instance;
	private static final Logger LOGGER = Logger.getLogger(LanguageService.class.getName());

	private final HashMap<Integer, Translation> translations = new HashMap<>();
	
	private int nextId = 0;

	private TranslationService() {
	}

	/**
	 * @return a reference to an example facade for Customer objects.
	 */
	public static TranslationService getInstance() {
		if (instance == null) {
			instance = new TranslationService();
			instance.ensureTestData();
		}
		return instance;
	}

	/**
	 * @return all available Customer objects.
	 */
	public synchronized List<Translation> findAll() {
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
	public synchronized List<Translation> findAll(String stringFilter) {
		return findAll(stringFilter, 0, translations.size());
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
	public synchronized List<Translation> findAll(String stringFilter, int start, int maxresults) {
		ArrayList<Translation> arrayList = new ArrayList<>();
		for (Translation translation : translations.values()) {
			try {
				boolean passesFilter = (stringFilter == null || stringFilter.isEmpty())
						|| translation.toString().toLowerCase().contains(stringFilter.toLowerCase());
				if (passesFilter) {
					arrayList.add(translation.clone());
				}
			} catch (CloneNotSupportedException ex) {
				Logger.getLogger(LanguageService.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		Collections.sort(arrayList, new Comparator<Translation>() {

			@Override
			public int compare(Translation o1, Translation o2) {
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
		return translations.size();
	}

	/**
	 * Deletes a customer from a system
	 *
	 * @param value
	 *            the Customer to be deleted
	 */
	public synchronized void delete(Translation value) {
		translations.remove(value.getId());
	}

	/**
	 * Persists or updates customer in the system. Also assigns an identifier
	 * for new Customer instances.
	 *
	 * @param entry
	 */
	public synchronized void save(Translation entry) {
		if (entry == null) {
			LOGGER.log(Level.SEVERE,
					"Customer is null. Are you sure you have connected your form to the application as described in tutorial chapter 7?");
			return;
		}
		if (entry.getId() == null) {
			entry.setId(nextId++);
		}
		translations.put(entry.getId(), entry);
	}
	
	public Translation generateTranslation(String sourceText, String destinationText, Integer lessonId, PhraseType type)
	{
		Translation translation = new Translation(lessonId);
		translation.setDestinationText(destinationText);
		translation.setSourceText(sourceText);
		translation.setType(type.getName());
		return translation;
	}

	/**
	 * Sample data generation
	 */
	public void ensureTestData() {
		if (findAll().isEmpty()) {
			save(generateTranslation("Jabłko", "an apple", 1, PhraseType.WORD));
			save(generateTranslation("Mysz", "a mouse", 1, PhraseType.WORD));
			save(generateTranslation("Gra planszowa", "a board game", 1, PhraseType.STATEMENT));
			save(generateTranslation("Kość", "a dice", 2, PhraseType.WORD));
			save(generateTranslation("Czyste lustro", "a clean mirrow", 2, PhraseType.STATEMENT));
			save(generateTranslation("Stół", "a table", 3, PhraseType.WORD));
			save(generateTranslation("Poduszka", "a pillow", 4, PhraseType.WORD));
		}
	}
}
