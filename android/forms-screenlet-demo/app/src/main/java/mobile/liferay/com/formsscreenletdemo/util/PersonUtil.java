package mobile.liferay.com.formsscreenletdemo.util;

/**
 * @author Luísa Lima
 */
public class PersonUtil {

	public static String getResourcePath(String serverURL, long userId) {
		String formEndpoint = "/o/api/p/user-account/%d";
		return serverURL + String.format(formEndpoint, userId);
	}

}
