package main.model.relation;

import main.model.Person;

public interface FriendRelation {
	public void addFriend(Person person) throws Exception;
	public void delFriend(Person person) throws Exception;
}
