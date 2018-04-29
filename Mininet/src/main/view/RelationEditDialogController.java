package main.view;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import main.MiniNet;
import main.model.*;
import main.model.relation.*;

public class RelationEditDialogController {

	@FXML
	private TableView<Person> personTable1;
	@FXML
	private TableView<Person> personTable2;
	@FXML
	private TableColumn<Person, String> nameColumn1;
	@FXML
	private TableColumn<Person, String> nameColumn2;
	@FXML
	private ChoiceBox<String> chB;
	@FXML
	private Label relationLabel;

	// Reference to the main application.
	private MiniNet miniNet;
	private Stage dialogStage;
	private Person person1;
	private Person person2;
	private boolean okClicked = false;

	@FXML
	private void initialize() {
		nameColumn1.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
		nameColumn2.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
		table1Listener();
		table2Listener();
	}

	private void table1Listener() {
		personTable1.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			relationLabel.setText("");
			handleLabel();
			choiceBoxListener(newValue);
		});
	}

	private void table2Listener() {
		personTable2.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			relationLabel.setText("");
			handleLabel();
			choiceBoxListener(newValue);

		});
	}

	private void handleLabel() {
		if (!personTable1.getSelectionModel().isEmpty()) {
			Person p1 = personTable1.getSelectionModel().getSelectedItem();
			Person p2 = personTable2.getSelectionModel().getSelectedItem();
			if (p1.connections.containsKey(p2)) {
				if (p1.connections.get(p2).equals("spouse")) {
					relationLabel.setText("Spouse");
				} else if (p1.connections.get(p2).equals("parents") || p1.connections.get(p2).equals("kids")) {
					relationLabel.setText("Parent & Child");
				} else if (p1.connections.get(p2).equals("friends")) {
					relationLabel.setText("Friends");
				} else if (p1.connections.get(p2).equals("colleagues")) {
					relationLabel.setText("Colleagues");
				} else if (p1.connections.get(p2).equals("classmates")) {
					relationLabel.setText("Classmates");
				}
			} else if (!p1.equals(p2))
				relationLabel.setText("No direct relationship");
		}
	}

	private void choiceBoxListener(Person person) {
		chB.getItems().clear();
		if (!personTable1.getSelectionModel().isEmpty() && !personTable1.getSelectionModel().isEmpty()) {
			Person p1 = personTable1.getSelectionModel().getSelectedItem();
			Person p2 = personTable2.getSelectionModel().getSelectedItem();
			if (relationLabel.getText().equals("No direct relationship")) {

			} else {
				if (p1 instanceof SpouseRelation && p2 instanceof SpouseRelation) {
					chB.getItems().add("Spouse");
				}
				if (p1 instanceof Adult && p2 instanceof Kid) {
					if (((Kid) p2).parents.size() < 2) {
						chB.getItems().add("Parent");
					}
				} else if (p2 instanceof Adult && p1 instanceof Kid) {
					if (((Kid) p1).parents.size() < 2) {
						chB.getItems().add("Parent");
					}
				}
				if (p1 instanceof Adult && p2 instanceof Adult) {
					chB.getItems().add("Friend");
				} else if (p1 instanceof Child && p2 instanceof Child) {
					chB.getItems().add("Friend");
				}
				if (p1 instanceof ColleagueRelation && p2 instanceof ColleagueRelation) {
					chB.getItems().add("Colleague");
				}
				if (p1 instanceof ClassmateRelation && p2 instanceof ClassmateRelation) {
					chB.getItems().add("Classmate");
				}
			}
		}
	}

	/**
	 * Sets the stage of this dialog.
	 * 
	 * @param dialogStage
	 */
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	public boolean isOkClicked() {
		return okClicked;
	}

	/**
	 * Called when the user clicks ok.
	 * 
	 * @throws Exception
	 */
	@FXML
	private void handleOk() throws Exception {
		boolean add = true;
		person1 = personTable1.getSelectionModel().getSelectedItem();
		person2 = personTable2.getSelectionModel().getSelectedItem();
		try {
			if (chB.getSelectionModel().getSelectedItem() == "Spouse") { // Spouse relation selected
				((Adult) person1).addSpouse(person2);
				((Adult) person2).addSpouse(person1);
			} else if (chB.getSelectionModel().getSelectedItem() == "Parent") { // parent relation selected
				if (person1 instanceof Adult && person2 instanceof Kid) {
					((Adult) person1).addDependent((Kid) person2);
					((Adult) person1).getSpouse().addDependent((Kid) person2);
				} else if (person2 instanceof Adult && person1 instanceof Kid) {
					((Adult) person2).getSpouse().addDependent((Kid) person1);
				}
			} else if (chB.getSelectionModel().getSelectedItem() == "Friend") { // friend relation selected
				person1.addFriend(person2);
				person2.addFriend(person1);
			} else if (chB.getSelectionModel().getSelectedItem() == "Colleague") { // colleague relation selected
				((Adult) person1).addColleague((Adult) person2);
				((Adult) person2).addColleague((Adult) person1);
			} else if (chB.getSelectionModel().getSelectedItem() == "Classmate") { // classmate relation selected
				if (person1 instanceof ClassmateRelation) {
					((ClassmateRelation) person1).addClassmate(person2);
					((ClassmateRelation) person2).addClassmate(person1);
				}
			}
			add = true;
		} catch (Exception e) {
			add = false;
			System.out.println(e.getMessage());
		}
		if (add) {
			handleLabel();
			okClicked = true;
		} else {
			// new dialog
		}
	}

	/**
	 * Called when the user clicks cancel.
	 */
	// relationLabel.setText("Spouse");
	// } else if (p1.connections.get(p2).equals("parents") ||
	// p1.connections.get(p2).equals("kids")) {
	// relationLabel.setText("Parent & Child");
	// } else if (p1.connections.get(p2).equals("friends")) {
	// relationLabel.setText("Friends");
	// } else if (p1.connections.get(p2).equals("colleagues")) {
	// relationLabel.setText("Colleagues");
	// } else if (p1.connections.get(p2).equals("classmates")) {
	// relationLabel.setText("Classmates");
	@FXML
	private void handleDisconnect() throws Exception {
		boolean add = true;
		person1 = personTable1.getSelectionModel().getSelectedItem();
		person2 = personTable2.getSelectionModel().getSelectedItem();
		try {
			if (relationLabel.getText() == "Spouse") { // Spouse relation
				((Adult) person1).delSpouse(person2);
				((Adult) person2).delSpouse(person1);
			} else if (relationLabel.getText() == "Parent & Child") { // parent relation
				if (person1 instanceof Adult && person2 instanceof Kid) {
					((Adult) person1).delDependent((Kid) person2);
					((Adult) person1).getSpouse().delDependent((Kid) person2);
				} else if (person2 instanceof Adult && person1 instanceof Kid) {
					((Adult) person2).delDependent((Kid) person1);
					((Adult) person2).getSpouse().delDependent((Kid) person1);
				}
			} else if (relationLabel.getText() == "Friends") { // friend relation selected
				person1.delFriend(person2);
				person2.delFriend(person1);
			} else if (relationLabel.getText() == "Colleagues") { // colleague relation selected
				((Adult) person1).delColleague((Adult) person2);
				((Adult) person2).delColleague((Adult) person1);
			} else if (relationLabel.getText() == "Classmates") { // classmate relation selected
				if (person1 instanceof ClassmateRelation) {
					((ClassmateRelation) person1).delClassmate(person2);
					((ClassmateRelation) person2).delClassmate(person1);
				}
			}
			add = true;
		} catch (Exception e) {
			add = false;
			System.out.println(e.getMessage());
		}
		if (add) {
			handleLabel();
			okClicked = true;
		} else {
			// new dialog
		}
	}

	@FXML
	private void handleCancel() {
		dialogStage.close();
	}

	public void setMiniNet(MiniNet miniNet) {
		this.miniNet = miniNet;

		// Add observable list data to the table
		personTable1.setItems(miniNet.getPersonData());
		personTable2.setItems(miniNet.getPersonData());
	}
}
