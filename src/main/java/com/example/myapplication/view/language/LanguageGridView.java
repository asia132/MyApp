package com.example.myapplication.view.language;


import java.util.List;

import com.example.myapplication.data.Language;
import com.example.myapplication.service.LanguageService;
import com.vaadin.annotations.Theme;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("serial")
@Theme("mytheme")
public class LanguageGridView extends FormLayout {

    private LanguageService service = LanguageService.getInstance();
    private Grid<Language> languageGrid = new Grid<>(Language.class);
    public LanguageEditView languageEditView = new LanguageEditView(this);
    public Button btnAddLanguage = new Button("Dodaj język");
    
    public LanguageGridView()
    {
        setSizeUndefined();
        languageGridSetup();
        languageEditView.setVisible(false);
        btnAddLanguageSetup();
        
        final VerticalLayout layout = new VerticalLayout();
        HorizontalLayout main = new HorizontalLayout(languageGrid, languageEditView);
        main.setSizeFull();
        main.setExpandRatio(languageGrid, 1);
        layout.addComponents(btnAddLanguage, main);
        addComponents(layout);
    }
    
    public void btnAddLanguageSetup() {

    	btnAddLanguage.setStyleName(ValoTheme.BUTTON_PRIMARY);
    	btnAddLanguage.setClickShortcut(KeyCode.SPACEBAR);
		
        btnAddLanguage.addClickListener(e -> {
        	languageGrid.asSingleSelect().clear();
        	languageEditView.setCustomer(new Language());
        });
    }
    
    public void languageGridSetup() {
    	updateList();
        languageGrid.setColumns("name");
        languageGrid.setSizeFull();
    	languageGrid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() == null) {
            	languageEditView.setVisible(false);
            } else {
            	languageEditView.setCustomer(event.getValue());
            }
        });
    }

    public void updateList() {
        List<Language> customers = service.findAll();
        languageGrid.setItems(customers);
    }
}
