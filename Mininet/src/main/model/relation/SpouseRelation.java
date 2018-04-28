package main.model.relation;

import main.model.Adult;

public interface SpouseRelation {

	public void addSpouse(Adult person) throws Exception;
	public void delSpouse(Adult person) throws Exception;
}
