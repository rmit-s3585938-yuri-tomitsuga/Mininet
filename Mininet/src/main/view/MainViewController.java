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

public class MainViewController {
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
	private Label parentLabel;
	@FXML
	private ImageView imageView;

	// Reference to the main application.
	private MiniNet miniNet;

	/**
	 * The constructor. The constructor is called before the initialize() method.
	 */
	public MainViewController() {
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

		});
	}

	public void showPersonDetails(Person person) {
		if (person != null) {
			// Fill the labels with info from the person object.
			this.imageLabel.setText("");
			this.imageView.setImage(null);
			if (person.getImage() == null) { // show txt if no image
				this.imageLabel.setText("No Image");
			} else if (person.getImage().isEmpty()) {
				this.imageLabel.setText("No Image");
			} else {
				Image image = new Image(new File(person.getImage()).toURI().toString());
				this.imageView.setImage(image);
			}
			nameLabel.setText(person.getName());
			genderLabel.setText(person.getGender());
			stateLabel.setText(person.getState());
			ageLabel.setText(Integer.toString(person.getAge()));
			statusLabel.setText(person.getStatus());
			// show parent or dependent
			if (person instanceof Adult) {
				if (((Adult) person).kid.size() > 0) {
					String s = "";
					for (int i = 0; i < ((Adult) person).kid.size(); i++) {
						s = s + "   " + ((Adult) person).kid.get(i).getName();
					}
					parentLabel.setText("Dependents:           " + s);
				} else
					parentLabel.setText("");
			} else if (person instanceof Kid) {
				if (((Kid) person).parents.size() > 0) {
					String s = "";
					for (int i = 0; i < ((Kid) person).parents.size(); i++) {
						s = s + "   " + ((Kid) person).parents.get(i).getName();
					}
					parentLabel.setText("Parents: 	                " + s);
				} else
					parentLabel.setText("");
			}

		} else {
			// Person is null, remove all the text.
			nameLabel.setText("");
			genderLabel.setText("");
			stateLabel.setText("");
			ageLabel.setText("");
			statusLabel.setText("");
			imageLabel.setText("");
			parentLabel.setText("");
		}
	}

	/**
	 * Called when the user clicks the add button. Opens a dialog to edit details
	 * for a new person.
	 */
	@FXML
	private void handleNewPerson() {
		Person tempPerson = new Person();
		// show another view
		boolean okClicked = miniNet.showPersonEditDialog(tempPerson, true);
		Person tempP = null;
		// create Person object depending on the age
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
		}
		showPersonDetails(selectedPerson);
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
	 * Called when the user clicks the edit relation button. Opens a dialog to edit
	 * details for a new person.
	 */
	@FXML
	private void handleEditRelation() {
		this.miniNet.showRelationEditDialog();
	}

	/**
	 * Is called by the main application to give a reference back to itself.
	 * 
	 * @param miniNet
	 */
	public void setMiniNet(MiniNet miniNet) {
		this.miniNet = miniNet;
		// Add observable list data to the table
		personTable.setItems(miniNet.getPersonData());
	}
}