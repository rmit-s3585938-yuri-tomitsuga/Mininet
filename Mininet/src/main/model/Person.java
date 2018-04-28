package main.model;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import main.model.*;
import main.model.relation.FriendRelation;

/**
 * Model class for a Person.
 *
 */
public class Person implements FriendRelation {

    private final StringProperty name;
    private final StringProperty gender;
    private final IntegerProperty age;
    private final StringProperty state;
    private final StringProperty status;
    private final StringProperty image;

    public HashMap<Person, String> connections = new HashMap<Person, String>();
    private List<Person> friends = new ArrayList<Person>();
    /**
     * Default constructor.
     */
    public Person() {
        this(null,null,null,null,null,null);
    }

    /**
     * Constructor with some initial data.
     * 
     */
    public Person(String name, String gender, Integer age, String state, String status, String image) {
        this.name = new SimpleStringProperty(name);
        this.gender = new SimpleStringProperty(gender);
        this.age = new SimpleIntegerProperty();
        this.age.setValue(age);
        this.state = new SimpleStringProperty(state);
        this.status = new SimpleStringProperty(status);
        this.image = new SimpleStringProperty(image);
       
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public StringProperty nameProperty() {
        return name;
    }

    public String getGender() {
        return gender.get();
    }

    public void setGender(String street) {
        this.gender.set(street);
    }

    public StringProperty genderProperty() {
        return gender;
    }

    public int getAge() {
        return age.get();
    }

    public void setAge(int age) {
        this.age.set(age);
    }

    public IntegerProperty ageProperty() {
        return age;
    }

    public String getState() {
        return state.get();
    }

    public void setState(String state) {
        this.state.set(state);
    }

    public StringProperty stateProperty() {
        return state;
    }
    
    public String getStatus() {
        return status.get();
    }

    public void setStatus(String status) {
        this.status.set(status);
    }

    public StringProperty statusProperty() {
        return status;
    }
    
    public String getImage() {
        return image.get();
    }

    public void setImage(String image) {
        this.image.set(image);
    }

    public StringProperty imageProperty() {
        return image;
    }

	@Override
	public void addFriend(Person person) throws Exception {
		// TODO Auto-generated method stub
		this.friends.add(person);
		this.connections.put(person, "friends");
	}

	@Override
	public void delFriend(Person person) throws Exception {
		// TODO Auto-generated method stub
		this.friends.remove(person);
		this.connections.remove(person);
	}

}