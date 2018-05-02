package main.model;

import java.util.ArrayList;
import java.util.List;

public class Kid extends Person {

	public List<Adult> parents = new ArrayList<Adult>();

	public Kid(String name, String gender, Integer age, String state, String status, String image) {
		super(name, gender, age, state, status, image);
	}

	public void addParent(Adult person) {
		parents.add(person);
		this.connections.put(person, "parents");
	}

	public void delParent(Adult person) {
		parents.remove(person);
		this.connections.remove(person);
	}
}