package main.view;

import java.io.File;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import main.MiniNet;
import main.model.*;

public class PersonViewController {
	@FXML
	private TableView<Person> personTable;
	@FXML
	private TableColumn<Person, String> nameColumn;

	@FXML
	private Label nameLabel;
	@FXML
	private Label genderLabel;
	@FXML
	private Label ageLabel;
	@FXML
	private Label stateLabel;
	@FXML
	private Label statusLabel;
	@FXML
	private Label imageLabel;
	@FXML
	private ImageView imageView;

	// Reference to the main application.
	private MiniNet miniNet;

	/**
	 * The constructor. The constructor is called before the initialize() method.
	 */
	public PersonViewController() {
	}

	/**
	 * Initializes the controller class. This method is automatically called after
	 * the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {
		// Initialize the person table.
		nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());

		// Clear person details.
		showPersonDetails(null);

		// Listen for selection changes and show the person details when changed.
		personTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			showPersonDetails(newValue);
			this.imageLabel.setText("");
			this.imageView.setImage(null);
			if (newValue.getImage() == null) {
				this.imageLabel.setText("No Image");
			} else if (newValue.getImage().isEmpty()) {
				this.imageLabel.setText("No Image");
			} else {
				// System.out.println(newValue.getImage());
				Image image = new Image(new File(newValue.getImage()).toURI().toString());
				this.imageView.setImage(image);
			}
		});
	}

	private void showPersonDetails(Person person) {
		if (person != null) {
			// Fill the labels with info from the person object.
			nameLabel.setText(person.getName());
			genderLabel.setText(person.getGender());
			stateLabel.setText(person.getState());
			ageLabel.setText(Integer.toString(person.getAge()));
			statusLabel.setText(person.getStatus());

		} else {
			// Person is null, remove all the text.
			nameLabel.setText("");
			genderLabel.setText("");
			stateLabel.setText("");
			ageLabel.setText("");
			statusLabel.setText("");
		}
	}

	/**
	 * Called when the user clicks the new button. Opens a dialog to edit details
	 * for a new person.
	 */
	@FXML
	private void handleNewPerson() {
		Person tempPerson = new Person();
		boolean okClicked = miniNet.showPersonEditDialog(tempPerson, true);
		Person tempP = null;
		if (okClicked) {
			if (tempPerson.getAge() > 16) {
				tempP = new Adult(tempPerson.getName(), tempPerson.getGender(), tempPerson.getAge(),
						tempPerson.getState(), tempPerson.getStatus(), tempPerson.getImage());
			} else if (tempPerson.getAge() <= 2) {
				tempP = new YoundChild(tempPerson.getName(), tempPerson.getGender(), tempPerson.getAge(),
						tempPerson.getState(), tempPerson.getStatus(), tempPerson.getImage());
			} else {
				tempP = new Child(tempPerson.getName(), tempPerson.getGender(), tempPerson.getAge(),
						tempPerson.getState(), tempPerson.getStatus(), tempPerson.getImage());
			}
			miniNet.getPersonData().add(tempP);
		}
	}

	/**
	 * Called when the user clicks the edit button. Opens a dialog to edit details
	 * for the selected person.
	 */
	@FXML
	private void handleEditPerson() {
		Person selectedPerson = personTable.getSelectionModel().getSelectedItem();
		if (selectedPerson != null) {
			boolean okClicked = miniNet.showPersonEditDialog(selectedPerson, false);
			if (okClicked) {
				showPersonDetails(selectedPerson);
			}

		} else {
			// Nothing selected.
		}
	}

	/**
	 * Called when the user clicks on the delete button.
	 */
	@FXML
	private void handleDeletePerson() {
		int selectedIndex = personTable.getSelectionModel().getSelectedIndex();
		if (selectedIndex >= 0) {
			personTable.getItems().remove(selectedIndex);
		}
	}

	/**
	 * Is called by the main application to give a reference back to itself.
	 * 
	 * @param miniNet
	 */

	/**
	 * Called when the user clicks the new button. Opens a dialog to edit details
	 * for a new person.
	 */
	@FXML
	private void handleEditRelation() {

		boolean okClicked = miniNet.showRelationEditDialog();
		if (okClicked) {

			// add relation
		}

	}

	public void setMiniNet(MiniNet miniNet) {
		this.miniNet = miniNet;

		// Add observable list data to the table
		personTable.setItems(miniNet.getPersonData());
	}
}