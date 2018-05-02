package main.model.relation;

import main.model.Adult;

public interface ColleagueRelation {
	public void addColleague(Adult person) throws Exception;
	public void delColleague(Adult person) throws Exception;
}
