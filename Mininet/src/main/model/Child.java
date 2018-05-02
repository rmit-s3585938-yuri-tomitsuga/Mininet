package main.model;

import java.util.ArrayList;

import main.model.relation.ClassmateRelation;

public class Child extends Kid implements ClassmateRelation {
	private ArrayList<Person> classmates = new ArrayList<Person>();

	public Child(String name, String gender, Integer age, String state, String status, String image) {
		super(name, gender, age, state, status, image);
	}

	/**
	 * method to add a classmate (between two children)
	 */
	@Override
	public void addClassmate(Person person) throws Exception {
		if (person instanceof Child) {
			classmates.add(person);
			this.connections.put(person, "classmates");
		} else {
			Exception tooYoundTeBeClassmateException = new Exception(
					this.getName() + " is too young to be classmate with " + person.getName() + "!");
			throw tooYoundTeBeClassmateException;
		}
	}

	@Override
	public void delClassmate(Person person) throws Exception {
		classmates.remove(person);
		this.connections.remove(person);
	}
}