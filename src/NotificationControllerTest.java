
import java.util.ArrayList;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalMemcacheServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.google.appengine.tools.development.testing.LocalTaskQueueTestConfig;
import com.m4.socialnetwork.model.controllers.NotificationController;
import com.m4.socialnetwork.model.javabeans.Notification;
import com.m4.socialnetwork.model.javabeans.User;

public class NotificationControllerTest {
	User usr = new User("maged", "maged", "maged", true);
	User usrFriend = new User("m", "m", "m", true);
	NotificationController notificationController=new NotificationController();
	private final LocalServiceTestHelper helper = new LocalServiceTestHelper(

	new LocalDatastoreServiceTestConfig(),
			new LocalMemcacheServiceTestConfig(),
			new LocalTaskQueueTestConfig());

	@BeforeClass
	public void setUp() {
		helper.setUp();
		notificationController.putNotification(usr.getId(), "compID", "Notification");
	}

	@AfterClass
	public void tearDown() {
		helper.tearDown();
	}

	@Test
	public void getNotifications() {
		ArrayList<Notification>arr=notificationController.getNotifications(usr.getId());
		Assert.assertNotNull(arr);
	}

	@Test
	public void getNotification() {
		Notification notification=new Notification("Notification Test Type",usr.getId(),"22","compID");
		String result=notificationController.getNotification(notification);
		Assert.assertNotNull(result);
	}


}
