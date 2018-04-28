package main.view;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.MiniNet;
import main.model.*;

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
	private ChoiceBox chB;

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
		chB.getItems().addAll("Spouse", "Parent", "Friend", "Colleague", "Classmate");
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
		person1 = personTable1.getSelectionModel().getSelectedItem();
		person2 = personTable2.getSelectionModel().getSelectedItem();
		if (chB.getSelectionModel().getSelectedIndex() == 0) { // Spouse relation selected
			((Adult) person1).addSpouse((Adult) person2);
			((Adult) person2).addSpouse((Adult) person1);
			System.out.println(((Adult) person1).getSpouse().getName());
			System.out.println(((Adult) person2).getSpouse().getName());
		} else if (chB.getSelectionModel().getSelectedIndex() == 1) { // parent relation selected
			if (person1 instanceof Adult && person2 instanceof Kid) {
				((Adult) person1).addDependent((Kid) person2);
			} else if (person2 instanceof Adult && person1 instanceof Kid) {
				((Adult) person2).addDependent((Kid) person1);
			}
		} else if (chB.getSelectionModel().getSelectedIndex() == 2) { // friend relation selected
			person1.addFriend(person2);
			person2.addFriend(person1);
		} else if (chB.getSelectionModel().getSelectedIndex() == 3) { // colleague relation selected
			((Adult) person1).addColleague((Adult) person2);
			((Adult) person2).addColleague((Adult) person1);
		}

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

	public void setMiniNet(MiniNet miniNet) {
		this.miniNet = miniNet;

		// Add observable list data to the table
		personTable1.setItems(miniNet.getPersonData());
		personTable2.setItems(miniNet.getPersonData());
	}
}
