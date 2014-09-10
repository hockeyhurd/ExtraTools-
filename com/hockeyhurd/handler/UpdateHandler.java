package com.hockeyhurd.handler;

import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

import com.hockeyhurd.util.LogHelper;
import com.hockeyhurd.util.Reference;

public class UpdateHandler {

	private final short currentBuild = Reference.BUILD;
	private short latestBuild;
	private boolean upToDate = true;
	
	// NOTE: Just add build number + .jar
	private static final String url = "http://75.68.113.97:8080/downloads/versions/ExtraTools+-1.1.";
	private String latestUrl = "";

	public UpdateHandler() {
	}

	public void check() {
		short copyBuild = currentBuild;
		String lastUrl;
		String copyUrl = lastUrl = url;
		copyUrl += copyBuild + ".jar";
		
		if (!exists(copyUrl)) {
			upToDate = true;
			this.latestBuild = copyBuild;
			this.latestUrl = "dev_build";
			return;
		}
		
		// Loop while there are still more 'updates found'
		while (exists(copyUrl)) {
			copyUrl = lastUrl;
			copyUrl += ++copyBuild + ".jar";
		}
		
		copyUrl = lastUrl;
		copyUrl += --copyBuild + ".jar";

		// Make sure the 'latest update' is not fake and if not, report 'not up to date'.
		if (copyBuild > currentBuild && exists(copyUrl)) upToDate = false;
		this.latestBuild = copyBuild;
		this.latestUrl = copyUrl;
	}
	
	public boolean getUpToDate() {
		return upToDate;
	}
	
	public short getLatestBuild() {
		return latestBuild;
	}
	
	public String getLatestURL() {
		return this.latestUrl;
	}
	
	public HashMap<Short,String> getMap() {
		HashMap<Short, String> ent = new HashMap<Short, String>();
		ent.put(getLatestBuild(), getLatestURL());
		return ent;
	}
	
	private boolean exists(String urlCheck) {
		try {
			HttpURLConnection.setFollowRedirects(false);
			// note : you may also need
			// HttpURLConnection.setInstanceFollowRedirects(false)
			HttpURLConnection con = (HttpURLConnection) new URL(urlCheck).openConnection();
			con.setRequestMethod("HEAD");
			
			// seconds length
			float seconds = 1.0f;
			// Convert to ms.
			seconds *= 1000;
			// Convert to int.
			int timeout = (int) seconds;
			
			con.setConnectTimeout(timeout);
			con.setReadTimeout(timeout);
			
			boolean exists = con.getResponseCode() == HttpURLConnection.HTTP_OK;
			
			// Make sure we disconnect connection to server.
			con.disconnect();
			return exists; 
		}
		catch (Exception e) {
			// e.printStackTrace();
			LogHelper.warn("Could not find requested url!", urlCheck);
			LogHelper.warn("Update server must be down or this build has not yet been released properly!");
			return false;
		}
	}

}
