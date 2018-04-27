package main.model;

import java.util.ArrayList;
import java.util.List;

public class Adult extends Person{

	private Adult spouse;

	private List<Dependent> children;

	public Adult(String name, String gender, Integer age, String state, String status) {
		super(name, gender, age, state, status);

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

	/**
	 * @return the children
	 */
	public List<Dependent> getChildren() {
		if (children == null) {
			children = new ArrayList<Dependent>();
		}
		return children;
	}

	/**
	 * @param children the children to add
	 */
	public void addChildren(Dependent child) {
		getChildren().add(child);
	}

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
