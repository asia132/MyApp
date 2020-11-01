package com.example.myapplication.view.lesson;

import java.util.List;

import com.example.myapplication.data.Lesson;
import com.example.myapplication.service.LessonService;
import com.example.myapplication.view.dictionary.DictionaryGridView;
import com.example.myapplication.view.dictionary.TranslationEditView;
import com.vaadin.annotations.Theme;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("serial")
@Theme("mytheme")
public class LessonGridView extends FormLayout {
	private LessonService service = LessonService.getInstance();
	
	public Grid<Lesson> lessonGrid = new Grid<>(Lesson.class);
	public LessonEditView lessonEditView = new LessonEditView(this);
	public DictionaryGridView dictionaryGridView = new DictionaryGridView(this);
	public TranslationEditView translationEditView = new TranslationEditView(dictionaryGridView);
	
	public Button btnAddLesson = new Button("Dodaj wpis");
	public TextField txtFilter = new TextField();
	
	public final HorizontalLayout toolBarLayout = new HorizontalLayout();
	public final VerticalLayout gridLayout = new VerticalLayout();

	public LessonGridView() {
		setSizeUndefined();
		lessonGridSetup();
		lessonEditView.setVisible(false);
		dictionaryGridView.setVisible(false);
		translationEditView.setVisible(false);
		btnAddLessonSetup();
		layoutSetup();
		txtFilterSetup();
		setMargin(true);
	}
	
	private void layoutSetup() {
		HorizontalLayout main = new HorizontalLayout(gridLayout, lessonEditView, dictionaryGridView, translationEditView);
		main.setSizeFull();
		main.setComponentAlignment(lessonEditView, Alignment.MIDDLE_CENTER);
		VerticalLayout layout = new VerticalLayout(toolBarLayout, main);
		layout.setComponentAlignment(main, Alignment.TOP_LEFT);
		addComponents(layout);
		setComponentAlignment(layout, Alignment.TOP_LEFT);
	}

	private void txtFilterSetup() {
		txtFilter.setPlaceholder("Filtrowanie po nazwie...");
		txtFilter.addValueChangeListener(e -> updateList());
		txtFilter.setValueChangeMode(ValueChangeMode.LAZY);

		Button clearFilterTextBtn = new Button("Wyczyść filtry");
		clearFilterTextBtn.addClickListener(e -> txtFilter.clear());
		CssLayout filtering = new CssLayout();
        filtering.addComponents(txtFilter, clearFilterTextBtn);
        filtering.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
        toolBarLayout.addComponents(filtering, btnAddLesson);
        gridLayout.addComponents(toolBarLayout, lessonGrid);
	}

	private void btnAddLessonSetup() {

		btnAddLesson.setStyleName(ValoTheme.BUTTON_PRIMARY);
		btnAddLesson.setClickShortcut(KeyCode.SPACEBAR);

		btnAddLesson.addClickListener(e -> {
			lessonGrid.asSingleSelect().clear();
			lessonEditView.setLesson(new Lesson());
		});
	}

	private void lessonGridSetup() {
		updateList();
		lessonGrid.setColumns("id", "name");
		lessonGrid.getColumn("id").setHidden(true);
		lessonGrid.setSizeFull();
		lessonGrid.asSingleSelect().addValueChangeListener(event -> {
			if (event.getValue() == null) {
				lessonEditView.setVisible(false);
				dictionaryGridView.setVisible(false);
			} else {
				Lesson lesson = event.getValue();
				lessonEditView.setLesson(lesson);
				dictionaryGridView.setVisible(true);
				dictionaryGridView.setLessonId(lesson.getId());
			}
		});
	}

	public void updateList() {
		List<Lesson> lessons = service.findAll(txtFilter.getValue());
		lessonGrid.setItems(lessons);
	}
}
