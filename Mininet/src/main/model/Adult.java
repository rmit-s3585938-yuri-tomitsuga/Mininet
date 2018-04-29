package main.model;

import java.util.ArrayList;
import java.util.List;

import main.model.relation.*;

public class Adult extends Person implements SpouseRelation, ParentRelation, ColleagueRelation, ClassmateRelation {

	private Adult spouse;

	private List<Kid> kid = new ArrayList<Kid>();
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

	@Override
	public boolean addSpouse(Person person) throws Exception {
		// TODO Auto-generated method stub
		boolean add;
		if (person instanceof Adult) {
			if (this.spouse == null) {
				this.spouse = (Adult) person;
				this.connections.put(person, "spouse");
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

	@Override
	public boolean delSpouse(Person person) throws Exception {
		// TODO Auto-generated method stub
		boolean add = true;
		if (this.kid.size() == 0) {
			this.spouse = null;
			this.connections.remove(person);
			add = true;
		}else {
			Exception noParentException = new Exception("This couple has children!");
			add = false;
			throw noParentException;
		}
		return add;
	}

	@Override
	public void addDependent(Kid person) throws Exception {
		// TODO Auto-generated method stub
		kid.add(person);
		this.connections.put(person, "kids");
	}

	@Override
	public void delDependent(Kid person) throws Exception {
		// TODO Auto-generated method stub
		kid.remove(person);
		this.connections.remove(person);
	}

	@Override
	public void addColleague(Adult person) throws Exception {
		// TODO Auto-generated method stub
		colleagues.add(person);
		this.connections.put(person, "colleagues");
	}

	@Override
	public void delColleague(Adult person) throws Exception {
		// TODO Auto-generated method stub
		colleagues.remove(person);
		this.connections.remove(person);
	}

	@Override
	public void addClassmate(Person person) throws Exception {
		// TODO Auto-generated method stub
		classmates.add(person);
		this.connections.put(person, "classmates");
	}

	@Override
	public void delClassmate(Person person) throws Exception {
		// TODO Auto-generated method stub
		classmates.remove(person);
		this.connections.remove(person);
	}

	/**
	 * @return the children
	 */
	// public List<Dependent> getChildren() {
	// if (children == null) {
	// children = new ArrayList<Dependent>();
	// }
	// return children;
	// }
	//
	// /**
	// * @param children the children to add
	// */
	// public void addChildren(Dependent child) {
	// getChildren().add(child);
	// }

	// @Override
	// public String toString() {
	// StringBuilder sb = new StringBuilder();
	// sb.append(getName());
	// sb.append(" ");
	// sb.append(getAge());
	// sb.append(" years old.\n");
	// if (getSpouse() != null) {
	// sb.append("Married with ");
	// sb.append(getSpouse().getName());
	// sb.append(".\n");
	// }
	//
	// if (getChildren().size() > 0) {
	// sb.append("Has children ");
	// for (Dependent d: getChildren()) {
	// sb.append(d.getName() + " ");
	// }
	// sb.append("\n");
	// }
	// if (getStatus() != null) {
	// sb.append(getStatus());
	// }
	//
	// return sb.toString();
	// }

	// @Override
	// public boolean delete() {
	// if (this.spouse != null) {
	// System.out.println(getName() + " cannot be deleted, he/she still has spouse,
	// deletion will break data integrity.");
	// return false;
	// }
	//
	// return true;
	// }

}
