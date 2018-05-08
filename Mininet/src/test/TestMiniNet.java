package test;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.core.StringEndsWith.endsWith;
import static org.hamcrest.core.StringContains.containsString;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import main.model.*;
import main.view.MainViewController;

public class TestMiniNet {

	Adult testAdult;
	Kid testKid;
	Child testChild;
	MainViewController mainViewController = new MainViewController();
	public TestMiniNet() {
		testAdult = new Adult(null, null, 20, null, null,null);
		testKid = new Kid(null, null, null, null, null,null);
		testChild = new Child(null, null, 3, null, null,null);
	}
	@Rule
    public ExpectedException thrown = ExpectedException.none();
	
	@Test
	public void testAddDependent() throws Exception {
		thrown.expectMessage(endsWith("is not married!"));
		testAdult.addDependent(testKid);
		fail("Exception not thrown");
	}
	
	@Test
	public void testAddClassmate() throws Exception {
		thrown.expectMessage(containsString("is too young to be classmate with"));
		testChild.addClassmate(testAdult);
		fail("Exception not thrown");
	}
	
	@Test
	public void testAddFriend() throws Exception {
		thrown.expectMessage("Their age gap is over 3!");
		testChild.addFriend(testAdult);
		fail("Exception not thrown");
	}
	
	@Test
	public void testSpouse() throws Exception {
		thrown.expectMessage(containsString("is too yound to get married!"));
		testAdult.addSpouse(testKid);
		fail("Exception not thrown");
	}
	
	@Test
	public void testMarriedSpouse() throws Exception {
		thrown.expectMessage(containsString("has been married!"));
		testAdult.addSpouse(testAdult);
		testAdult.addSpouse(testAdult);
		fail("Exception not thrown");
	}


}
