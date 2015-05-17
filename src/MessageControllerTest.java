import java.util.ArrayList;

import junit.framework.Assert;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalMemcacheServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.google.appengine.tools.development.testing.LocalTaskQueueTestConfig;
import com.m4.socialnetwork.model.controllers.MessageController;
import com.m4.socialnetwork.model.controllers.UserController;
import com.m4.socialnetwork.model.javabeans.Conversation;
import com.m4.socialnetwork.model.javabeans.User;

public class MessageControllerTest {
	UserController usercontroller = new UserController();
	User user1 = new User("maged", "maged", "maged", true);
	User user2 = new User("m", "m", "m", true);
	User user3 = new User("m1", "m1", "m1", true);
	MessageController messagecontroller = new MessageController();
	private final LocalServiceTestHelper helper = new LocalServiceTestHelper(

	new LocalDatastoreServiceTestConfig(),
			new LocalMemcacheServiceTestConfig(),
			new LocalTaskQueueTestConfig());

	@BeforeClass
	public void setUp() {
		helper.setUp();
		UserController usercontroller = new UserController();
		usercontroller.insertUser(user1);
		usercontroller.insertUser(user2);
		usercontroller.insertUser(user3);
	}

	@AfterClass
	public void tearDown() {
		helper.tearDown();
	}

	@Test
	public void testCreateConversation() {

		String result = messagecontroller.createConversation(user1.getId(),
				user2.getId());
		System.out.println("created " + result);
		Assert.assertNotNull(result);
	}

	@Test
	public void conversationExist() {
		String result = messagecontroller.conversationExist(user1.getId(),
				user2.getId());
		System.out.println("res " + result);
		Assert.assertNotNull(result);
		// throw new RuntimeException("Test not implemented");
	}

	@Test
	public void getConversation() {
		messagecontroller.createConversation(user1.getId(), user3.getId());
		String result = messagecontroller.conversationExist(user1.getId(),
				user3.getId());
		System.out.println("result conversation " + result);
		Conversation result2 = messagecontroller.getConversation(result);
		Assert.assertNotNull(result2);

		// throw new RuntimeException("Test not implemented");
	}

	//
	@Test
	public void testGetUserConversationIndividuals() {
		ArrayList<Conversation> conversations = messagecontroller
				.getUserConversationIndividuals(user1.getId());
		Assert.assertNotNull(conversations);
		// throw new RuntimeException("Test not implemented");
	}

	//
	@Test
	public void testInsertConversationGroup() {
		String result = messagecontroller.insertConversationGroup(
				user1.getId(), "chat 1");
		Assert.assertNotNull(result);
		// throw new RuntimeException("Test not implemented");
	}

	//
	@Test
	public void createConversaionGroup() {
		ArrayList<String> arr = new ArrayList();
		arr.add(user2.getId());
		arr.add(user3.getId());
		String result = messagecontroller.createConversaionGroup(user1.getId(),
				"chat 1", arr);
		Assert.assertNotNull(result);

		// throw new RuntimeException("Test not implemented");
	}

}
