package main.view;

import java.io.File;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import main.MiniNet;
import main.model.Person;

/**
 * Dialog to edit details of a person.
 */
public class PersonEditDialogController {

	MiniNet miniNet;

	@FXML
	private TextField nameField;
	@FXML
	private ChoiceBox<String> genderBox;
	@FXML
	private ChoiceBox<String> stateBox;
	@FXML
	private TextField ageField;
	@FXML
	private TextField statusField;
	@FXML
	private Text imageField;

	private Stage dialogStage;
	private Person person;
	private boolean okClicked = false;

	/**
	 * initializes the choice box items.
	 */
	@FXML
	private void initialize() {
		genderBox.getItems().addAll("M", "F");
		stateBox.getItems().addAll("ACT", "NSW", "NT", "QLD", "SA", "TAS", "VIC", "WA");
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

		if (!add) {
			nameField.setText(person.getName());
			genderBox.getSelectionModel().select(person.getGender());
			stateBox.getSelectionModel().select(person.getState());
			ageField.setText(Integer.toString(person.getAge()));
			statusField.setText(person.getStatus());
			imageField.setText(person.getImage());
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
	 * Choose file from file system
	 */
	@FXML
	private void handleChoose() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Choose Image File");
		fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JPEG", "*.jpeg"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("BMP", "*.bmp"),
                new FileChooser.ExtensionFilter("GIF", "*.gif"),
                new FileChooser.ExtensionFilter("PNG", "*.png")
            );
		File file = fileChooser.showOpenDialog(this.miniNet.primaryStage);
		this.imageField.setText(file.getAbsolutePath());
	}

	/**
	 * Called when the user clicks ok.
	 */
	@FXML
	private void handleOk() {
		try {
			// checks if age between 0,150
			if (Integer.parseInt(ageField.getText()) > 150 || Integer.parseInt(ageField.getText()) < 0) {
				Exception InvalidAgeException = new Exception("Age has to be between 0 and 150!");
				throw InvalidAgeException;
			}
			File file = new File(imageField.getText());
			if (!file.exists()&&!imageField.getText().isEmpty()) {
				Exception ImageFoundException = new Exception("Image not found!");
				throw ImageFoundException;
			}
			person.setName(nameField.getText());
			person.setGender(genderBox.getSelectionModel().getSelectedItem());
			person.setState(stateBox.getSelectionModel().getSelectedItem());
			person.setAge(Integer.parseInt(ageField.getText()));
			person.setStatus(statusField.getText());
			person.setImage(imageField.getText());
			okClicked = true;
			dialogStage.close();
			// throws exceptions
		} catch (NumberFormatException e) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setContentText("An age has to be an integer!");
			alert.showAndWait();
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setContentText(e.getMessage());
			alert.showAndWait();
		}
	}

	/**
	 * Called when the user clicks cancel.
	 */
	@FXML
	private void handleCancel() {
		dialogStage.close();
	}

	public void setMiniNet(MiniNet miniNet) {
		this.miniNet = miniNet;
	}
}
