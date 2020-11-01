package com.example.myapplication.view.dictionary;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import com.example.myapplication.data.PhraseType;
import com.example.myapplication.data.Translation;
import com.example.myapplication.service.TranslationService;
import com.example.myapplication.service.UserService;
import com.vaadin.data.Binder;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("serial")
public class TranslationEditView extends FormLayout {
	public TextField txtSource = new TextField();
	public TextField txtDestination = new TextField();
	public ComboBox<String> cbType = new ComboBox<>("Typ frazy");

	private Button btnSave = new Button("Save");
	private Button btnDelete = new Button("Delete");

	private final TranslationService translationservice = TranslationService.getInstance();
	private final UserService userService = UserService.getInstance();
	
	private Translation translation;
	private DictionaryGridView parentView;
	private Binder<Translation> binder = new Binder<>(Translation.class);

	public TranslationEditView(DictionaryGridView parentView) {
		this.parentView = parentView;

		setSizeUndefined();
		HorizontalLayout buttons = new HorizontalLayout(btnSave, btnDelete);
		HorizontalLayout textFields = new HorizontalLayout(txtSource, txtDestination);
		addComponents(textFields, cbType, buttons);

		bindFormFields();
		btnSaveSetup();
		btnDeleteSetup();
		cbTypeSetup();
		textFieldSetup();
	}
	
	private void textFieldSetup() {
		txtSource.setCaption(userService.getCurrentSourceLanguageName());
		txtDestination.setCaption(userService.getCurrentDestinationLanguageName());
		txtSource.focus();
	}

	private void bindFormFields() {
		String errorMessage = "Pole nie może być puste";
		binder.forField(txtSource).asRequired(errorMessage).bind(Translation::getSourceText, Translation::setSourceText);
		binder.forField(txtDestination).asRequired(errorMessage).bind(Translation::getDestinationText, Translation::setDestinationText);
		binder.forField(cbType).asRequired(errorMessage).bind(Translation::getType, Translation::setType);
	}

	private void cbTypeSetup() {
		List <String> items = new ArrayList<>(PhraseType.values().length);
		Stream.of(PhraseType.values()).forEach(type -> items.add(type.getName()));
		cbType.setItems(items);
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

	public void setTranslation(Translation translation) {
		this.translation = translation;
		binder.setBean(translation);

		// Show delete button for only customers already in the database
		btnDelete.setVisible(translation.isPersisted());
		setVisible(true);
		txtSource.selectAll();
	}

	private void delete() {
		translationservice.delete(translation);
		parentView.updateList();
		setVisible(false);
	}

	private void save() {
		translationservice.save(translation);
		parentView.updateList();
		setVisible(false);
	}

}
