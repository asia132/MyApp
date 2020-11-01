package com.example.myapplication.view.dictionary;

import java.util.List;

import com.example.myapplication.data.Translation;
import com.example.myapplication.service.TranslationService;
import com.example.myapplication.view.lesson.LessonGridView;
import com.vaadin.annotations.Theme;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.shared.ui.ValueChangeMode;
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
public class DictionaryGridView extends FormLayout {

	private TranslationService service = TranslationService.getInstance();
	private Grid<Translation> translationGrid = new Grid<>(Translation.class);
	public Button btnAddTranslation = new Button("Dodaj wpis");
	public Button btnBack = new Button("Wróć");
	private TextField txtFilter = new TextField();
	private Button clearFilterTextBtn = new Button("Wyczyść filtry");
	private LessonGridView parentView;
	
	public final HorizontalLayout lButton = new HorizontalLayout();
	
	private int lessonId;

	public DictionaryGridView(LessonGridView parentView) {
		this.parentView = parentView;
		setSizeUndefined();
		setMargin(false);
		dictionaryGridSetup();
		btnAddLanguageSetup();

		final VerticalLayout layout = new VerticalLayout();
		HorizontalLayout main = new HorizontalLayout(translationGrid);
		main.setSizeFull();
		main.setExpandRatio(translationGrid, 1);
		layout.addComponents(lButton, btnAddTranslation, main);
		addComponents(layout);
		
		txtFilterSetup();
	}

	private void txtFilterSetup() {
		txtFilter.setPlaceholder("Filtrowanie po nazwie...");
		txtFilter.addValueChangeListener(e -> updateList());
		txtFilter.setValueChangeMode(ValueChangeMode.LAZY);
		
		clearFilterTextBtn.addClickListener(e -> txtFilter.clear());
		CssLayout filtering = new CssLayout();
        filtering.addComponents(txtFilter, clearFilterTextBtn);
        filtering.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);

        btnBack.setVisible(false);
        btnBack.setClickShortcut(KeyCode.ESCAPE);
        btnBack.addClickListener(e -> getBackToLessonGrid());
        
        lButton.addComponents(btnBack, filtering, btnAddTranslation);
	}

	private void btnAddLanguageSetup() {

		btnAddTranslation.setStyleName(ValoTheme.BUTTON_PRIMARY);
		btnAddTranslation.setClickShortcut(KeyCode.SPACEBAR);

		btnAddTranslation.addClickListener(e -> {
			translationGrid.asSingleSelect().clear();
			parentView.translationEditView.setTranslation(new Translation(lessonId));
			parentView.lessonEditView.setVisible(false);
			parentView.gridLayout.setVisible(false);
		});
	}
	
	private void getBackToLessonGrid()
	{
		btnBack.setVisible(false);
		parentView.translationEditView.setVisible(false);
		parentView.lessonEditView.setVisible(true);
		parentView.gridLayout.setVisible(true);
	}

	private void dictionaryGridSetup() {
		updateList();
		translationGrid.setColumns("sourceText", "destinationText", "type");
		translationGrid.setSizeFull();
		translationGrid.asSingleSelect().addValueChangeListener(event -> {
			if (event.getValue() == null) {
				getBackToLessonGrid();
			} else {
				parentView.translationEditView.setTranslation(event.getValue());
				parentView.lessonEditView.setVisible(false);
				parentView.gridLayout.setVisible(false);
				btnBack.setVisible(true);
			}
		});
	}

	public void updateList() {
		List<Translation> translations = service.findAll(txtFilter.getValue());
		translationGrid.setItems(translations);
	}
	
	public void setLessonId(Integer lessonId) {
		this.lessonId=lessonId;
	}

	public Integer getLessonId() {
		return lessonId;
	}
}
