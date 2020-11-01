package com.example.myapplication.view.teacher;

import java.util.ArrayList;
import java.util.List;

import com.example.myapplication.data.Translation;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
public class TestView extends FormLayout {
	private Label lblOriginal = new Label();
	private TextField tfTranslation = new TextField();
	private VerticalLayout vlText = new VerticalLayout(lblOriginal,tfTranslation);
	private Panel pText = new Panel(vlText);

	private HorizontalLayout hlStatistics = new HorizontalLayout();
	private Label lblCorrectCount = new Label();
	private Label lblWrongCount = new Label();
	private Label lblLeftCount = new Label();

	private List<Translation> alltranslations = new ArrayList<>();
	private List<Translation> wrongtranslations = new ArrayList<>();

	private int correctCount = 0;
	private int wrongCount = 0;
	private int leftCount = 0;

	public TestView() {
		addComponents(pText, hlStatistics);
		setComponentAlignment(hlStatistics, Alignment.BOTTOM_RIGHT);
		setComponentAlignment(pText, Alignment.MIDDLE_CENTER);
		fillStatictics();
		
	}

	public void fillStatictics() {
		hlStatistics.setSpacing(true);
		hlStatistics.addComponents(new Label("["), lblCorrectCount, new Label(":"), lblWrongCount, new Label("/"),
				lblLeftCount, new Label("]"));
	}
	
	public void setTranslationList(int lessonId)
	{
		
	}
}
