package com.example.myapplication.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.example.myapplication.data.Lesson;

public class LessonService {
	private static LessonService instance;
	private static final Logger LOGGER = Logger.getLogger(LessonService.class.getName());

	private final HashMap<Integer, Lesson> lessons = new HashMap<>();

	private final UserService userService = UserService.getInstance();

	private int nextId = 0;

	private LessonService() {
	}

	/**
	 * @return a reference to an example facade for Customer objects.
	 */
	public static LessonService getInstance() {
		if (instance == null) {
			instance = new LessonService();
			instance.ensureTestData();
		}
		return instance;
	}

	/**
	 * @return all available Customer objects.
	 */
	public synchronized List<Lesson> findAll() {
		return findAll(null);
	}

	/**
	 * Finds all Customer's that match given filter.
	 *
	 * @param stringFilter filter that returned objects should match or null/empty
	 *                     string if all objects should be returned.
	 * @return list a Customer objects
	 */
	public synchronized List<Lesson> findAll(String stringFilter) {
		return findAll(stringFilter, 0, lessons.size());
	}

	/**
	 * Finds all Customer's that match given filter and limits the resultset.
	 *
	 * @param stringFilter filter that returned objects should match or null/empty
	 *                     string if all objects should be returned.
	 * @param start        the index of first result
	 * @param maxresults   maximum result count
	 * @return list a Customer objects
	 */
	public synchronized List<Lesson> findAll(String stringFilter, int start, int maxresults) {
		ArrayList<Lesson> arrayList = new ArrayList<>();
		for (Lesson lesson : lessons.values()) {
			try {
				boolean passesFilter = (stringFilter == null || stringFilter.isEmpty())
						|| lesson.toString().toLowerCase().contains(stringFilter.toLowerCase());
				if (passesFilter) {
					arrayList.add(lesson.clone());
				}
			} catch (CloneNotSupportedException ex) {
				Logger.getLogger(LanguageService.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		Collections.sort(arrayList, new Comparator<Lesson>() {

			@Override
			public int compare(Lesson o1, Lesson o2) {
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
		return lessons.size();
	}

	public String getLessonNameOfIndex(Integer index) {
		return lessons.get(index).getName();
	}

	/**
	 * Deletes a customer from a system
	 *
	 * @param value the Customer to be deleted
	 */
	public synchronized void delete(Lesson value) {
		lessons.remove(value.getId());
	}

	/**
	 * Persists or updates customer in the system. Also assigns an identifier for
	 * new Customer instances.
	 *
	 * @param entry
	 */
	public synchronized void save(Lesson entry) {
		if (entry == null) {
			LOGGER.log(Level.SEVERE,
					"Customer is null. Are you sure you have connected your form to the application as described in tutorial chapter 7?");
			return;
		}
		if (entry.getId() == null) {
			entry.setId(nextId++);
		}
		lessons.put(entry.getId(), entry);
	}

	/**
	 * Sample data generation
	 */
	public void ensureTestData() {
		if (findAll().isEmpty()) {
			final String[] names = new String[] { "Lekcja 1", "Lekcja 2", "Lekcja 3", "Lekcja 4" };
			for (String name : names) {
				Lesson lesson = new Lesson();
				lesson.setName(name);
				lesson.setDestinationLanguageid(userService.getCurrentDestinationLanguageId());
				lesson.setSourceLanguageid(userService.getCurrentSourceLanguageId());
				save(lesson);
			}
		}
	}
}
