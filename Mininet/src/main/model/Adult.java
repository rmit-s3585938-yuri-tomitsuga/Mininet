package main.model;

import java.util.ArrayList;
import java.util.List;

import main.model.relation.*;

public class Adult extends Person implements SpouseRelation, ParentRelation, ColleagueRelation, ClassmateRelation {

	private Adult spouse;

	// Collections
	public List<Kid> kid = new ArrayList<Kid>();
	private List<Adult> colleagues = new ArrayList<Adult>();
	private ArrayList<Person> classmates = new ArrayList<Person>();

	public Adult(String name, String gender, Integer age, String state, String status, String image) {
		super(name, gender, age, state, status, image);
	}

	/**
	 * @return the spouse
	 */
	public Adult getSpouse() {
		return spouse;
	}

	/**
	 * method to add a spouse
	 * 
	 * @param person
	 *            the person that is to be added
	 */
	@Override
	public boolean addSpouse(Person person) throws Exception {
		boolean add;
		if (person instanceof Adult) {
			if (this.spouse == null) {
				this.spouse = (Adult) person;
				this.connections.put(person, "spouse");// put this person into the overall connnection
				add = true;
			} else {
				Exception marriedException = new Exception(this.getName() + " has been married!");
				add = false;
				throw marriedException;
			}
		} else {
			Exception tooYoungToMarryException = new Exception(this.getName() + " is too yound to get married!");
			add = false;
			throw tooYoungToMarryException;
		}
		return add;
	}

	/**
	 * method to delete a spouse
	 */
	@Override
	public boolean delSpouse(Person person) throws Exception {
		boolean add = true;
		if (this.kid.size() == 0) {
			this.spouse = null;
			this.connections.remove(person);
			add = true;
		} else {
			Exception noParentException = new Exception("This couple has children!");
			add = false;
			throw noParentException;
		}
		return add;
	}

	@Override
	public void addDependent(Kid person) throws Exception {
		if (this.spouse == null) {
			Exception notMarriedException = new Exception(this.getName() + " is not married!");
			throw notMarriedException;
		}
		kid.add(person);
		this.connections.put(person, "kids");
	}

	@Override
	public void delDependent(Kid person) throws Exception {
		kid.remove(person);
		this.connections.remove(person);
	}

	@Override
	public void addColleague(Adult person) throws Exception {
		colleagues.add(person);
		this.connections.put(person, "colleagues");
	}

	@Override
	public void delColleague(Adult person) throws Exception {
		colleagues.remove(person);
		this.connections.remove(person);
	}

	@Override
	public void addClassmate(Person person) throws Exception {
		classmates.add(person);
		this.connections.put(person, "classmates");
	}

	@Override
	public void delClassmate(Person person) throws Exception {
		classmates.remove(person);
		this.connections.remove(person);
	}
}