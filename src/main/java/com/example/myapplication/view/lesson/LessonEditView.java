package com.example.myapplication.view.lesson;

import com.example.myapplication.data.Lesson;
import com.example.myapplication.service.LessonService;
import com.vaadin.data.Binder;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("serial")
public class LessonEditView extends FormLayout {
		public TextField txtName = new TextField("Nazwa");

		private Button btnSave = new Button("Save");
		private Button btnDelete = new Button("Delete");

		private LessonService service = LessonService.getInstance();
		private Lesson lesson;
		private LessonGridView parentView;
		private Binder<Lesson> binder = new Binder<>(Lesson.class);

		public LessonEditView(LessonGridView parentView) {
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
					.bind(Lesson::getName, Lesson::setName);
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

		public void setLesson(Lesson lesson) {
			this.lesson = lesson;
			binder.setBean(this.lesson);

			// Show delete button for only customers already in the database
			btnDelete.setVisible(this.lesson.isPersisted());
			setVisible(true);
			txtName.selectAll();
		}

		private void delete() {
			service.delete(lesson);
			parentView.updateList();
			setVisible(false);
		}

		private void save() {
			service.save(lesson);
			parentView.updateList();
			setVisible(false);
		}
}
