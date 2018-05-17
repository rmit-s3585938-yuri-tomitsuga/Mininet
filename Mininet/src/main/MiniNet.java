package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.model.*;
import main.view.PersonEditDialogController;
import main.view.MainViewController;
import main.view.RelationEditDialogController;

public class MiniNet extends Application {

	public Stage primaryStage;
	private AnchorPane mainView;
	// Collection of all people in MiniNet
	private ObservableList<Person> personData = FXCollections.observableArrayList();
	// Reference to the main view controller
	public MainViewController controller;

	/**
	 * reads files to load initial data
	 * 
	 */
	public MiniNet() throws Exception {
		readPeople();
		readRelation();
	}

	public void readPeople() {
		File file = new File("people.txt");
		try {
			Scanner sc = new Scanner(file);
			while (sc.hasNextLine()) {
				String line = sc.nextLine();
				String[] data = line.split(", ");// divide line by " ,"
				if (Integer.parseInt(data[4]) > 16) {// if age >16, adult
					personData.add(new Adult(data[0], data[3], Integer.parseInt(data[4]), data[5], data[2], data[1]));
				} else if (Integer.parseInt(data[4]) <= 2) {// if age <2, young child
					personData.add(
							new YoundChild(data[0], data[3], Integer.parseInt(data[4]), data[5], data[2], data[1]));
				} else {// if 2<= age <=16, child
					personData.add(new Child(data[0], data[3], Integer.parseInt(data[4]), data[5], data[2], data[1]));
				}
			}
			sc.close();
		} catch (FileNotFoundException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText("people.txt file not found!");
			alert.showAndWait();
		}
	}

	public void readRelation() throws Exception {
		File file = new File("relations.txt");
		try {
			Scanner sc = new Scanner(file);
			while (sc.hasNextLine()) {
				Person p1 = null;
				Person p2 = null;
				String line = sc.nextLine();
				String[] data = line.split(", ");
				for (int i = 0; i < personData.size(); i++) {
					if (personData.get(i).getName().equals(data[0])) {// read person 1
						p1 = personData.get(i);
					} else if (personData.get(i).getName().equals(data[1])) {// read person 2
						p2 = personData.get(i);
					}
				} // read relation
				if (data[2].equals("couple")) {
					((Adult) p1).addSpouse((Adult) p2);
					((Adult) p2).addSpouse((Adult) p1);
				} else if (data[2].equals("parent")) {
					if (p1.getAge() > p2.getAge()) {
						((Adult) p1).addDependent((Kid) p2);
						((Kid) p2).addParent((Adult) p1);
					} else {
						((Adult) p2).addDependent((Kid) p1);
						((Kid) p1).addParent((Adult) p2);
					}
				} else if (data[2].equals("friends")) {
					p1.addFriend(p2);
					p2.addFriend(p1);
				} else if (data[2].equals("colleagues")) {
					((Adult) p2).addColleague((Adult) p1);
					((Adult) p1).addColleague((Adult) p2);
				} else if (data[2].equals("classmates")) {
					if (p2 instanceof Adult) {
						((Adult) p2).addClassmate((Adult) p1);
						((Adult) p1).addClassmate((Adult) p2);
					} else {
						((Child) p2).addClassmate((Child) p1);
						((Child) p1).addClassmate((Child) p2);
					}
				}
			}
			sc.close();
		} catch (FileNotFoundException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText("relations.txt file not found!");
			alert.showAndWait();
		}
	}

	// Getter of collection of people
	public ObservableList<Person> getPersonData() {
		return personData;
	}

	@Override
	public void start(Stage primaryStage) throws IOException {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("MiniNet");

		initMainView();

	}
	

	/**
	 * Initializes the main view.
	 */
	public void initMainView() {
		try {
			// Load layout from fxml file.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MiniNet.class.getResource("view/MainView.fxml"));
			mainView = loader.load();
			// Show the scene containing the main view.
			Scene scene = new Scene(mainView);
			primaryStage.setScene(scene);
			primaryStage.show();
			// Controller for the view
			MainViewController controller = loader.getController();
			// references to each other
			controller.setMiniNet(this);
			this.controller = controller;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Opens a dialog to edit details for the specified person. If the user clicks
	 * OK, the changes are saved into the provided person object and true is
	 * returned.
	 * 
	 * @param person
	 *            the person object to be edited
	 * @return true if the user clicked OK, false otherwise.
	 */
	public boolean showPersonEditDialog(Person person, boolean add) {
		try {
			// Load the fxml file and create a new stage for the dialog.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MiniNet.class.getResource("view/PersonEditDialog.fxml"));
			AnchorPane page = (AnchorPane) loader.load();

			// Create the dialog Stage.
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Edit Person");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			// Set the person into the controller.
			PersonEditDialogController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setPerson(person, add);
			controller.setMiniNet(this);
			// Show the dialog and wait until the user closes it
			dialogStage.showAndWait();

			return controller.isOkClicked();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Opens a dialog to edit the relation between 2 persons. If the user clicks OK,
	 * the changes are saved and true is returned.
	 * 
	 * @return true if the user clicked OK, false otherwise.
	 */
	public boolean showRelationEditDialog() {
		try {
			// Load the fxml file and create a new stage for the dialog.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MiniNet.class.getResource("view/RelationEditDialog.fxml"));
			AnchorPane page = (AnchorPane) loader.load();

			// Create the dialog Stage.
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Edit Relation");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);
			RelationEditDialogController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setMiniNet(this);
			// Show the dialog and wait until the user closes it
			dialogStage.showAndWait();
			return controller.isOkClicked();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Returns the main stage.
	 * 
	 * @return
	 */
	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public static void main(String[] args) {
		launch(args);
	}
}