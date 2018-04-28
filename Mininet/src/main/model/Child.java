package main.model;

import java.util.ArrayList;

import main.model.relation.ClassmateRelation;

public class Child extends Kid implements ClassmateRelation{

	private ArrayList<Person> classmates = new ArrayList<Person>();
	public Child() {
		// TODO Auto-generated constructor stub
	}

	public Child(String name, String gender, Integer age, String state, String status) {
		super(name, gender, age, state, status);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void addClassmate(Person person) throws Exception {
		// TODO Auto-generated method stub
		classmates.add(person);
	}

	@Override
	public void delClassmate(Person person) throws Exception {
		// TODO Auto-generated method stub
		classmates.remove(person);
	}

}
