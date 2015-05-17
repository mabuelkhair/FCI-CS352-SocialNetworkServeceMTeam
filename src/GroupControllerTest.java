import java.util.ArrayList;

import junit.framework.Assert;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalMemcacheServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.google.appengine.tools.development.testing.LocalTaskQueueTestConfig;
import com.m4.socialnetwork.model.controllers.GroupController;
import com.m4.socialnetwork.model.controllers.UserController;
import com.m4.socialnetwork.model.javabeans.Group;
import com.m4.socialnetwork.model.javabeans.User;

public class GroupControllerTest {
	UserController usercontroller = new UserController();
	User user1 = new User("maged", "maged", "maged", true);
	User user2 = new User("m", "m", "m", true);
	User user3 = new User("m1", "m1", "m1", true);
	private final LocalServiceTestHelper helper = new LocalServiceTestHelper(

	new LocalDatastoreServiceTestConfig(),
			new LocalMemcacheServiceTestConfig(),
			new LocalTaskQueueTestConfig());
	Group newGroup;

	@BeforeClass
	public void setUp() {
		helper.setUp();
//		UserController usercontroller = new UserController();
	}

	@AfterClass
	public void tearDown() {
		helper.tearDown();
	}

	@Test
	public void createGroup() {
		 usercontroller.insertUser(user1);
		 usercontroller.insertUser(user2);
		 usercontroller.insertUser(user3);
		 ArrayList<String>groupMembers=new ArrayList<String>();
		 groupMembers.add(user2.getId());
		 groupMembers.add(user3.getId());
		 
		Group g1=new Group("gr1","Mostafa lovers",groupMembers,user1.getId());
		GroupController groupController=new GroupController();
		Group result=groupController.createGroup(g1);
		Assert.assertNotNull(result);
	}

}
