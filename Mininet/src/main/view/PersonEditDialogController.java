package main.view;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.model.Adult;
import main.model.Person;

/**
 * Dialog to edit details of a person.
 */
public class PersonEditDialogController {

	@FXML
	private TextField nameField;
	@FXML
	private TextField genderField;
	@FXML
	private TextField stateField;
	@FXML
	private TextField ageField;
	@FXML
	private TextField statusField;

	private Stage dialogStage;
	private Person person;
	private boolean okClicked = false;

	@FXML
	private void initialize() {
	}

	/**
	 * Sets the stage of this dialog.
	 * 
	 * @param dialogStage
	 */
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	/**
	 * Sets the person to be edited in the dialog.
	 * 
	 * @param person
	 */
	public void setPerson(Person person, boolean add) {
		this.person = person;

		if(!add) {
		nameField.setText(person.getName());
		genderField.setText(person.getGender());
		stateField.setText(person.getState());
		ageField.setText(Integer.toString(person.getAge()));
		statusField.setText(person.getStatus());
		}
	}

	/**
	 * Returns true if the user clicked OK, false otherwise.
	 * 
	 * @return
	 */
	public boolean isOkClicked() {
		return okClicked;
	}

	/**
	 * Called when the user clicks ok.
	 */
	@FXML
	private void handleOk() {
		person.setName(nameField.getText());
		person.setGender(genderField.getText());
		person.setState(stateField.getText());
		person.setAge(Integer.parseInt(ageField.getText()));
		person.setStatus(statusField.getText());

		okClicked = true;
		dialogStage.close();
	}

	/**
	 * Called when the user clicks cancel.
	 */
	@FXML
	private void handleCancel() {
		dialogStage.close();
	}

}
