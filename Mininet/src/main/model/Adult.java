package main.model;

import java.util.ArrayList;
import java.util.List;

import main.model.relation.*;

public class Adult extends Person implements SpouseRelation, ParentRelation,ColleagueRelation,ClassmateRelation{

	private Adult spouse;

	private List<Kid> kid = new ArrayList<Kid>();
	private List<Adult> colleagues = new ArrayList<Adult>();
	private ArrayList<Person> classmates = new ArrayList<Person>();

	public Adult(String name, String gender, Integer age, String state, String status, String image) {
		super(name, gender, age, state, status,image);
	}

	/**
	 * @return the spouse
	 */
	public Adult getSpouse() {
		return spouse;
	}

	/**
	 * @param spouse the spouse to set
	 */
	public void getMarryWith(Adult spouse) {
//		if (this.spouse != null && spouse != this.spouse) {
//			throw new IllegalArgumentException(this.getName()
//								+ " is married with " + this.getSpouse().getName());
//		} else if (spouse.getSpouse() != null && this != spouse.spouse) {
//			throw new IllegalArgumentException(spouse.getName()
//								+ " is married with "
//								+ spouse.getSpouse().getName());
//		}

		if (spouse.getSpouse() == null) {
			this.spouse = spouse;
		}
		else {
			System.out.println("Married already!");
		}

	}

	@Override
	public void addSpouse(Adult person) throws Exception {
		// TODO Auto-generated method stub
		this.spouse = (Adult) person;
		this.connections.put(person, "spouse");
	}
	@Override
	public void delSpouse(Adult person) throws Exception {
		// TODO Auto-generated method stub
		this.spouse = null;
		this.connections.remove(person);
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
		this.connections.put(person,"colleagues");
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
		this.connections.put(person,"classmates");
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
//	public List<Dependent> getChildren() {
//		if (children == null) {
//			children = new ArrayList<Dependent>();
//		}
//		return children;
//	}
//
//	/**
//	 * @param children the children to add
//	 */
//	public void addChildren(Dependent child) {
//		getChildren().add(child);
//	}

//	@Override
//	public String toString() {
//		StringBuilder sb = new StringBuilder();
//		sb.append(getName());
//		sb.append(" ");
//		sb.append(getAge());
//		sb.append(" years old.\n");
//		if (getSpouse() != null) {
//			sb.append("Married with ");
//			sb.append(getSpouse().getName());
//			sb.append(".\n");
//		}
//
//		if (getChildren().size() > 0) {
//			sb.append("Has children ");
//			for (Dependent d: getChildren()) {
//				sb.append(d.getName() + " ");
//			}
//			sb.append("\n");
//		}
//		if (getStatus() != null) {
//			sb.append(getStatus());
//		}
//
//		return sb.toString();
//	}

//	@Override
//	public boolean delete() {
//		if (this.spouse != null) {
//			System.out.println(getName() + " cannot be deleted, he/she still has spouse, deletion will break data integrity.");
//			return false;
//		}
//
//		return true;
//	}

}
