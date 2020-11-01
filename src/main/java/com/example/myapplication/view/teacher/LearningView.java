package com.example.myapplication.view.teacher;

import java.util.List;

import com.example.myapplication.data.Lesson;
import com.example.myapplication.service.LessonService;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
public class LearningView extends FormLayout {
	public Grid<Lesson> lessonGrid = new Grid<>(Lesson.class);
	private LessonService service = LessonService.getInstance();
	private TestView testView = new TestView();
	private VerticalLayout mainLayout = new VerticalLayout(lessonGrid, testView);
	
	public LearningView()
	{
		lessonGridSetup();
		addComponents(mainLayout);
		testView.setVisible(false);
	}

	private void lessonGridSetup() {
		updateList();
		lessonGrid.setColumns("id", "name");
		lessonGrid.getColumn("id").setHidden(true);
		lessonGrid.setSizeFull();
		lessonGrid.asSingleSelect().addValueChangeListener(event -> {
			if (event.getValue() != null) {
				Lesson lesson = event.getValue();
				testView.setTranslationList(lesson.getId());
				lessonGrid.setVisible(false);
				testView.setVisible(true);
			}
		});
	}

	public void updateList() {
		List<Lesson> lessons = service.findAll();
		lessonGrid.setItems(lessons);
	}
}
