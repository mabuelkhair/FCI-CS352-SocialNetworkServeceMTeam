import java.util.ArrayList;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.m4.socialnetwork.model.javabeans.*;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalMemcacheServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.google.appengine.tools.development.testing.LocalTaskQueueTestConfig;
import com.m4.socialnetwork.model.controllers.NotificationController;
import com.m4.socialnetwork.model.controllers.UserController;

public class UserTest {
	/** Awesome testing class */
	User usr = new User("maged", "maged", "maged", true);
	User usrFriend = new User("m", "m", "m", true);
	UserController usercontroller = new UserController();
	private final LocalServiceTestHelper helper = new LocalServiceTestHelper(

	new LocalDatastoreServiceTestConfig(),
			new LocalMemcacheServiceTestConfig(),
			new LocalTaskQueueTestConfig());

	@BeforeClass
	public void setUp() {
		helper.setUp();
		
	}

	@AfterClass
	public void tearDown() {
		helper.tearDown();
	}

	@Test
	public void testGetUser() {
		usercontroller.insertUser(usr);
		User tst = usercontroller.getUser("maged", "maged");
		System.out.println(tst.getEmail());
		Assert.assertNotNull(tst);
	}

	@Test
	public void testGetUser2() {
		User tst = usercontroller.getUser("maged");
		System.out.println(tst.getEmail());
		Assert.assertNotNull(tst);
	}

	@Test
	public void testGetUserById() {
		User tst = usercontroller.getUser("maged");
		User tst2 = usercontroller.getUserById(tst.getId());
		Assert.assertNotNull(tst2);
	}

	@Test
	public void testSendFriedRequest() {
		usercontroller.insertUser(usr);
		usercontroller.insertUser(usrFriend);
		usercontroller.insertUser(usrFriend);
		Assert.assertTrue(usercontroller.sendFriedRequest(usr.getEmail(),
				usrFriend.getEmail()));
	}

	@Test
	public void testRequestExistBefore() {
		Assert.assertTrue(!usercontroller.requestExistBefore(usr.getEmail(),
				usrFriend.getEmail()));
	}

	@Test
	public void testAcceptFriendRequest() {
		usercontroller.insertUser(usr);
		usercontroller.insertUser(usrFriend);
		usercontroller.sendFriedRequest(usr.getEmail(), usrFriend.getEmail());
		Assert.assertTrue(usercontroller.acceptFriendRequest(usr.getEmail(),
				usrFriend.getEmail()));
	}

	@Test
	public void testGetAllUserFriends() {
		User usrX = new User("x", "x", "x", true);
		User usrY = new User("y", "y", "y", true);
		usercontroller.sendFriedRequest(usrX.getEmail(),
				usrY.getEmail());
		usercontroller.acceptFriendRequest(usrX.getEmail(), usrY.getEmail());
		ArrayList<User> friends = usercontroller.getAllUserFriends(usrX
				.getEmail());
		ArrayList<User> realFriends = new ArrayList<User>();
		realFriends.add(usrY);
		Assert.assertEquals(friends, realFriends);
	}
}
