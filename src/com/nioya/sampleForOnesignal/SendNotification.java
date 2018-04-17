package com.nioya.sampleForOnesignal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.log4j.Logger;
import org.json.JSONObject;



public class SendNotification {
	
	final static Logger logger = Logger.getLogger(SendNotification.class);
	
	
	public static void main(String[] args) {
		sendNotification("PLAYERID","TITLE","CONTENT");
		System.out.println("Done");
	}
	
	public static void sendNotification(String targetId, String title, String content) {
		logger.info("==== STARTED NOTIFICATION FUNCTION -> Notification ID: " + targetId + "===========");
		CloseableHttpClient closeableHttpClient = HttpClientBuilder.create().build();
		String callUri = "https://onesignal.com/api/v1/notifications";
		HttpPost httpPost = new HttpPost(callUri);
		httpPost.addHeader("Content-Type", "application/json");
		httpPost.addHeader("charset", "utf-8");
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("app_id", "c90c6a5e-08c9-45b4-9b6d-cd363f4600b1");

		JSONObject jsonObjectForTitle = new JSONObject();
		JSONObject jsonObjectForContent = new JSONObject();
		jsonObject.put("headings", jsonObjectForTitle.put("en", title));
		jsonObject.put("contents", jsonObjectForContent.put("en", content));

		jsonObject.append("include_player_ids", targetId);
		jsonObject.put("android_sound", "money");
		StringEntity xmlEntity = new StringEntity(jsonObject.toString(), "UTF-8");
		xmlEntity.setContentEncoding("UTF-8");
		xmlEntity.setContentType("application/json");
		httpPost.setEntity(xmlEntity);

		HttpResponse response = null;
		try {
			response = closeableHttpClient.execute(httpPost);
		} catch (ClientProtocolException e) {
			logger.error("NOTIFICATION FUNCTION "+targetId + " / EXECUTE Phase - ClientProtocolException" + e.getMessage());
		} catch (IOException e) {
			logger.error("NOTIFICATION FUNCTION "+targetId + " / EXECUTE Phase - IOException" + e.getMessage());
		}

		if (response.getStatusLine().getStatusCode() != 200) {
			logger.info("NOTIFICATION FUNCTION "+targetId + " FAILED / RESPONSE HAS AN ERROR - Response"
					+ response.getStatusLine().getReasonPhrase());

		} else {
			logger.info("NOTIFICATION FUNCTION "+targetId + " SUCCESS");

			BufferedReader br = null;
			try {
				br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));
			} catch (UnsupportedOperationException e1) {
				logger.error(
						"NOTIFICATION FUNCTION "+targetId + " / RESPONSE PARSE -  UnsupportedOperationException" + e1.getMessage());
			} catch (IOException e1) {
				logger.error("NOTIFICATION FUNCTION "+targetId + " / RESPONSE PARSE - IOException" + e1.getMessage());
			}

			String output = null;
			try {
				output = br.readLine();
			} catch (IOException e) {
				logger.error("NOTIFICATION FUNCTION "+targetId + " / RESPONSE READ - IOException" + e.getMessage());
			}

			JSONObject result = new JSONObject(output);
			int recipients = (int) result.get("recipients");
			String receipt_id = (String) result.get("id");
			if (recipients >= 1) {
				//Do Something
			} else {
				//Do Something
			}


		}
		try {
			closeableHttpClient.close();
		} catch (IOException e) {
			logger.error("NOTIFICATION FUNCTION "+targetId + " / CONNECTION CLOSE - IOException" + e.getMessage());
		}		
		logger.info("==== FINISHED NOTIFICATION FUNCTION-> Notification ID: " +targetId + "===========");
	}

}
