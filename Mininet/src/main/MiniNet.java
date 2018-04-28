package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.model.*;
import main.view.PersonEditDialogController;
import main.view.PersonViewController;
import main.view.RelationEditDialogController;

public class MiniNet extends Application {

	private Stage primaryStage;
	private AnchorPane mainView;

	private ObservableList<Person> personData = FXCollections.observableArrayList();

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
				String[] data = line.split(", ");
				if (Integer.parseInt(data[4]) > 16) {
					personData.add(new Adult(data[0], data[3], Integer.parseInt(data[4]), data[5], data[2], data[1]));
				} else if (Integer.parseInt(data[4]) <= 2) {
					personData.add(
							new YoundChild(data[0], data[3], Integer.parseInt(data[4]), data[5], data[2], data[1]));
				} else {
					personData.add(new Child(data[0], data[3], Integer.parseInt(data[4]), data[5], data[2], data[1]));
				}
			}
			sc.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
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
					if (personData.get(i).getName().equals(data[0])) {
						p1 = personData.get(i);
					} else if (personData.get(i).getName().equals(data[1])) {
						p2 = personData.get(i);
					}
				}
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
			e.printStackTrace();
		}
	}

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
			// Load root layout from fxml file.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MiniNet.class.getResource("view/MainView.fxml"));
			mainView = loader.load();

			// Show the scene containing the root layout.
			Scene scene = new Scene(mainView);
			primaryStage.setScene(scene);
			primaryStage.show();
			// Show the list of persons.
			PersonViewController controller = loader.getController();
			controller.setMiniNet(this);
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
			// Load the fxml file and create a new stage for the popup dialog.
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

			// Show the dialog and wait until the user closes it
			dialogStage.showAndWait();

			return controller.isOkClicked();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean showRelationEditDialog() {
		try {
			// Load the fxml file and create a new stage for the popup dialog.
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

			// Set the person into the controller.
			RelationEditDialogController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setMiniNet(this);
			// controller.setPerson(person,add);

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