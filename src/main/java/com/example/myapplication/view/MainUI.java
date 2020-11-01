package com.example.myapplication.view;

import javax.servlet.annotation.WebServlet;

import com.example.myapplication.service.UserService;
import com.example.myapplication.view.language.LanguageGridView;
import com.example.myapplication.view.lesson.LessonGridView;
import com.example.myapplication.view.teacher.LearningView;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
@Theme("mytheme")
public class MainUI extends UI {
	public final VerticalLayout lMain = new VerticalLayout();
	public final HorizontalLayout lButton = new HorizontalLayout();
	public final HorizontalLayout lLabel = new HorizontalLayout();
	public final VerticalLayout lView = new VerticalLayout();

	public Button btnLanguage = new Button("Wybierz język");
	public Button btnLesson = new Button("Edytuj lekcje");
	public Button btnStart = new Button("Zacznij naukę");

	public Label lblCurrentLanguage = new Label();
	public Label lblCurrentUser = new Label();

	public LanguageGridView languageView = new LanguageGridView();
	public LessonGridView lessonView = new LessonGridView();
	public LearningView learningView = new LearningView();

	private final UserService userService = UserService.getInstance();

	@Override
	protected void init(VaadinRequest vaadinRequest) {
		setMainLayout();
		userLabelSetup();
		btnLanguageSetup();
		btnLessonSetup();
		btnStartSetup();
		lViewSetup();
		lButtonSetup();
	}

	private void lViewSetup() {
		languageView.setVisible(false);
		lessonView.setVisible(false);
		learningView.setVisible(false);
		
		HorizontalLayout toolBar = new HorizontalLayout(lButton, lLabel);
		toolBar.setComponentAlignment(lLabel, Alignment.TOP_RIGHT);
		toolBar.setComponentAlignment(lButton, Alignment.TOP_LEFT);
		toolBar.setWidth("100%");
		
		lView.addComponents(toolBar, languageView, lessonView, learningView);
	}

	private void btnLanguageSetup() {
		btnLanguage.addClickListener(e -> {
			languageView.setVisible(true);
			lessonView.setVisible(false);
			learningView.setVisible(false);
		});
	}

	private void btnLessonSetup() {
		btnLesson.addClickListener(e -> {
			languageView.setVisible(false);
			lessonView.setVisible(true);
			learningView.setVisible(false);
		});

	}

	private void btnStartSetup() {
		btnStart.addClickListener(e -> {
			languageView.setVisible(false);
			lessonView.setVisible(false);
			learningView.setVisible(true);
		});
	}

	private void userLabelSetup() {
		lblCurrentUser.setValue(userService.getCurrentUserName());
		lblCurrentLanguage.setValue("[" + userService.getCurrentSourceLanguageName() + "-" + userService.getCurrentDestinationLanguageName() + "]");
		lLabel.addComponents(lblCurrentUser, lblCurrentLanguage);
		lLabel.setSpacing(true);
	}

	private void lButtonSetup() {
		lButton.addComponents(btnLanguage, btnLesson, btnStart);
		lButton.setComponentAlignment(btnLanguage, Alignment.MIDDLE_CENTER);
		lButton.setComponentAlignment(btnLesson, Alignment.MIDDLE_CENTER);
		lButton.setComponentAlignment(btnStart, Alignment.MIDDLE_CENTER);
	}

	/**
	 * Set Main layout properties and content
	 */
	private void setMainLayout() {
		lMain.setWidth("100%");
		lMain.setHeight("100%");
		lMain.setMargin(false);
		lMain.setSpacing(true);

		lMain.addComponents(lView);
		setContent(lMain);
	}

	@WebServlet(urlPatterns = "/*", name = "MainServlet", asyncSupported = true)
	@VaadinServletConfiguration(ui = MainUI.class, productionMode = false)
	public static class MainServlet extends VaadinServlet {
	}
}
