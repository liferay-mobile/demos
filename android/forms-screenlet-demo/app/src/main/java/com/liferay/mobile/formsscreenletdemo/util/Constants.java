package com.liferay.mobile.formsscreenletdemo.util;

/**
 * @author Victor Oliveira
 */
public interface Constants {
	long FORM_INSTANCE_ID = 38395;
	long CONTENT_SPACE_ID = 20126;

	String BLOG_POSTING_ENDPOINT = "/o/api/p/content-space/%d/blog-posting";
	String FORM_ENDPOINT = "/o/api/p/form/%d?embedded=structure";
	String PERSON_ENDPOINT = "/o/api/p/user-account/%d";
	String ENTRY_ID_KEY = "entryId";
	String THING_ID_KEY = "thingId";
}

