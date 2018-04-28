package main.model;

import java.util.List;

public class Kid extends Person{

	public List<Adult> parents;
	public Kid() {
		// TODO Auto-generated constructor stub
	}

	public Kid(String name, String gender, Integer age, String state, String status) {
		// TODO Auto-generated constructor stub
		super(name, gender, age, state, status);
	}
	
	public void addParent(Adult person) {
		parents.add(person);
	}

}
