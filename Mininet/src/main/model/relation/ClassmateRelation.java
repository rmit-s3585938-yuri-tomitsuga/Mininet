package main.model.relation;

import main.model.Adult;
import main.model.Person;

public interface ClassmateRelation {
	public void addClassmate(Person person) throws Exception;
	public void delClassmate(Person person) throws Exception;
}
