package com.hockeyhurd.handler;

import java.net.HttpURLConnection;
import java.net.URL;

import com.hockeyhurd.util.Reference;

public class UpdateHandler {

	private final short currentBuild = Reference.build;
	private short latestBuild;
	private boolean upToDate = true;
	
	// NOTE: Just add build number + .jar
	private static final String url = "http://75.68.113.97:8080/downloads/versions/ExtraTools+-1.1.";
	private String latestUrl = "";

	public UpdateHandler() {
	}

	public void check() {
		short copyVersion = currentBuild;
		String lastUrl;
		String copyUrl = lastUrl = url;
		copyUrl += copyVersion + ".jar";
		
		// Loop while there are still more 'updates found'
		while (exists(copyUrl)) {
			copyUrl = lastUrl;
			copyUrl += ++copyVersion + ".jar";
		}

		// Make sure the 'latest update' is not fake and if not, report 'not up to date'.
		if (copyVersion != currentBuild && exists(copyUrl)) upToDate = false;
		this.latestBuild = copyVersion;
		this.latestUrl = copyUrl;
	}
	
	public boolean hasUpdate() {
		return !upToDate ? true : false;
	}
	
	public String getLatestURL() {
		return this.latestUrl;
	}
	
	private boolean exists(String urlCheck) {
		try {
			HttpURLConnection.setFollowRedirects(false);
			// note : you may also need
			// HttpURLConnection.setInstanceFollowRedirects(false)
			// System.err.println("Checking url: " + urlCheck);
			HttpURLConnection con = (HttpURLConnection) new URL(urlCheck).openConnection();
			con.setRequestMethod("HEAD");
			
			return (con.getResponseCode() == HttpURLConnection.HTTP_OK); 
		}
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}
