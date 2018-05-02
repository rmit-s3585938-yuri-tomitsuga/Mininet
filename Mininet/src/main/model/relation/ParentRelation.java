package main.model.relation;

import main.model.*;

public interface ParentRelation {
	public void addDependent(Kid person) throws Exception;
	public void delDependent(Kid person) throws Exception;
}
