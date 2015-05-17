

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalMemcacheServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.google.appengine.tools.development.testing.LocalTaskQueueTestConfig;
import com.m4.socialnetwork.model.controllers.*;
import com.m4.socialnetwork.model.javabeans.*;
public class NotificationAcceptFriendRequestCommandTest {
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
  public void getNotification() {
	  User user1=new User("m","m","m",true);
	  Notification notification=new Notification("Notification Test Type",user1.getId(),"22","compID");
	  NotificationAcceptFriendRequestCommand notificationCont=new NotificationAcceptFriendRequestCommand();
	  String result=notificationCont.getNotification(notification);
	  Assert.assertNotNull(result);
//    throw new RuntimeException("Test not implemented");
  }
}
