package main;

import java.io.IOException;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
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
    
    public MiniNet() {
        // Add some sample data
        personData.add(new Child("Tom", "M", 15, "VIC", "Student"));
        personData.add(new Adult("Rose", "F", 65, "VIC", "Retired"));
        personData.add(new Adult("Emily", "F", 25, "VIC", "Work in an office"));
        personData.add(new YoundChild("Jack", "M", 1, "VIC", "Baby"));

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
     * Opens a dialog to edit details for the specified person. If the user
     * clicks OK, the changes are saved into the provided person object and true
     * is returned.
     * 
     * @param person the person object to be edited
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
            controller.setPerson(person,add);

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
//            controller.setPerson(person,add);

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
     * @return
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}