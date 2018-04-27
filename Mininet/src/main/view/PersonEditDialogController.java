package main.view;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
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
	public void setPerson(Person person) {
		this.person = person;

		nameField.setText(person.getName());
		genderField.setText(person.getGender());
		stateField.setText(person.getState());
		if (!nameField.getText().isEmpty()) {
			ageField.setText(Integer.toString(person.getAge()));
		}
		statusField.setText(person.getStatus());
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
		// if (isInputValid()) {
		person.setName(nameField.getText());
		person.setGender(genderField.getText());
		person.setState(stateField.getText());
		person.setAge(Integer.parseInt(ageField.getText()));
		person.setStatus(statusField.getText());

		okClicked = true;
		dialogStage.close();
		// }
	}

	/**
	 * Called when the user clicks cancel.
	 */
	@FXML
	private void handleCancel() {
		dialogStage.close();
	}

	/**
	 * Validates the user input in the text fields.
	 * 
	 * @return true if the input is valid
	 */
	// private boolean isInputValid() {
	// String errorMessage = "";
	//
	// if (nameField.getText() == null || nameField.getText().length() == 0) {
	// errorMessage += "No valid name!\n";
	// }
	// if (lastNameField.getText() == null || lastNameField.getText().length() == 0)
	// {
	// errorMessage += "No valid last name!\n";
	// }
	// if (stateField.getText() == null || stateField.getText().length() == 0) {
	// errorMessage += "No valid street!\n";
	// }
	//
	// if (ageField.getText() == null || ageField.getText().length() == 0) {
	// errorMessage += "No valid postal code!\n";
	// } else {
	// // try to parse the postal code into an int.
	// try {
	// Integer.parseInt(ageField.getText());
	// } catch (NumberFormatException e) {
	// errorMessage += "No valid postal code (must be an integer)!\n";
	// }
	// }
	//
	// if (statusField.getText() == null || statusField.getText().length() == 0) {
	// errorMessage += "No valid city!\n";
	// }
	//
	//
	// if (errorMessage.length() == 0) {
	// return true;
	// } else {
	// // Show the error message.
	// Dialogs.create()
	// .title("Invalid Fields")
	// .masthead("Please correct invalid fields")
	// .message(errorMessage)
	// .showError();
	// return false;
	// }
	// }
}
