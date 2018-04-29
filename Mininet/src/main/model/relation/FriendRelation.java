package main.model.relation;

import main.model.Person;

public interface FriendRelation {
	public boolean addFriend(Person person) throws Exception;
	public boolean delFriend(Person person) throws Exception;
}
