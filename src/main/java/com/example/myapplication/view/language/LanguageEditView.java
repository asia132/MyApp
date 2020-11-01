package com.example.myapplication.view.language;

import com.example.myapplication.data.Language;
import com.example.myapplication.service.LanguageService;
import com.vaadin.data.Binder;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("serial")
public class LanguageEditView extends FormLayout {
	public TextField txtName = new TextField("Nazwa");

	private Button btnSave = new Button("Save");
	private Button btnDelete = new Button("Delete");

	private LanguageService service = LanguageService.getInstance();
	private Language language;
	private LanguageGridView parentView;
	private Binder<Language> binder = new Binder<>(Language.class);

	public LanguageEditView(LanguageGridView parentView) {
		this.parentView = parentView;

		setSizeUndefined();
		HorizontalLayout buttons = new HorizontalLayout(btnSave, btnDelete);
		addComponents(txtName, buttons);

		bindFormFields();
		btnSaveSetup();
		btnDeleteSetup();
	}

	private void bindFormFields() {
		binder.forField(txtName).withValidator(name -> !name.isEmpty(), "Nazwa nie może być pusta")
				.bind(Language::getName, Language::setName);
	}

	private void btnDeleteSetup() {

		btnDelete.addClickListener(e -> this.delete());
		btnSave.setClickShortcut(KeyCode.DELETE);
	}

	private void btnSaveSetup() {
		btnSave.setStyleName(ValoTheme.BUTTON_PRIMARY);
		btnSave.setClickShortcut(KeyCode.ENTER);
		btnSave.addClickListener(e -> {
			if (binder.validate().isOk())
				this.save();
		});
	}

	public void setCustomer(Language language) {
		this.language = language;
		binder.setBean(language);

		// Show delete button for only customers already in the database
		btnDelete.setVisible(language.isPersisted());
		setVisible(true);
		txtName.selectAll();
	}

	private void delete() {
		service.delete(language);
		parentView.updateList();
		setVisible(false);
	}

	private void save() {
		service.save(language);
		parentView.updateList();
		setVisible(false);
	}
}
