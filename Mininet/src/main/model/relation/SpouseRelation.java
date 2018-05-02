package main.model.relation;

import main.model.Person;

public interface SpouseRelation {
	public boolean addSpouse(Person person) throws Exception;
	public boolean delSpouse(Person person) throws Exception;
}
