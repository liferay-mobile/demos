package com.liferay.mobile.formsscreenletdemo.util;

/**
 * @author Luísa Lima
 */
public class ResourceUtil {

	public static String getResourcePath(String serverURL, long id, ResourceType resourceType) {
		String resourceEndpoint = "";

		switch (resourceType) {
			case BLOGS:
				resourceEndpoint = Constants.COLLECTION_ENDPOINT;
				break;
			case FORMS:
				resourceEndpoint = Constants.FORM_ENDPOINT;
				break;
			case PERSON:
				resourceEndpoint = Constants.PERSON_ENDPOINT;
				break;
		}

		return serverURL + String.format(resourceEndpoint, id);
	}
}
