package de.fhb.n00b.TwitterBot.start;

import org.json.*;
import org.scribe.builder.*;
import org.scribe.builder.api.*;
import org.scribe.model.*;
import org.scribe.oauth.*;

import de.fhb.n00b.TwitterBot.controller.*;
import de.fhb.n00b.TwitterBot.exception.*;


public class TwitterBot {
		
	/**
	 * @param args Übergabeparameter (-setup: neue Nutzer konfigurieren)
	 */
	public static void main(String[] args) {

		try {
			Response response;
			long lastMentionId;
			// Der Train ist so vorgegeben, wenn man immer wieder 
			// bei der service anfangen würde müsste man jedesmal
			// einen Cast benutzen
			OAuthService service = new ServiceBuilder()
				.provider(TwitterApi.class)
				.apiKey("Z44a11Ip54TevlqHw")
				.apiSecret("qVZo7prFZ6QjH1EyWpGbcN0Ciii5fbN0GexCrDM6Zw")
				.build();
			
			Token accessToken = Config.readConfig();
			
			if(accessToken == null || (args.length == 1 && "-setup".equals(args[0]))  ) {
				Token newToken = Config.runSetup(service);
				Config.writeConfig(newToken);
				accessToken = Config.readConfig();
			}
			if(accessToken == null) throw new ConfigException("Konnte keine Information aus der Konfigurationsdatei laden.");
			
			
			response = User.loginUser(service, accessToken);
			if(!response.isSuccessful()) {
				throw new ConfigException("Bitte Setup neu ausführen. Daten wohl nicht mehr aktuell.");
			} else {
				try {
					JSONObject json = new JSONObject(response.getBody());
					System.out.println("Nutzer "+json.get("screen_name")+" alias "+json.get("name")+" wurde angemeldet.");
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			
			response = Mention.getLastMention(service, accessToken);
			try {
				JSONArray mentions = new JSONArray(response.getBody());
				if(mentions.length() > 0) {
					JSONObject lastMention = mentions.getJSONObject(0);
					lastMentionId = (Long) lastMention.get("id");
				} else {
					lastMentionId = 1;
				}
			} catch (JSONException e) {
				throw new CriticalErrorException("Antwort vom Server beschädigt.");
			}
			
			System.out.println("Check starten... alle 30 Sekunden auf Erwähnungen prüfen.");
			long timestamp = System.currentTimeMillis();
			while(true) {
				if(System.currentTimeMillis()-timestamp < 1*30*1000) {
					try {
						Thread.currentThread();
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				} else {
					response = Mention.getMentionsSince(service, accessToken, lastMentionId);
					try {
						JSONArray mentions = new JSONArray(response.getBody());
						for (int i=0;i<mentions.length();i++) {
							JSONObject mention = mentions.getJSONObject(i);
							JSONObject user = mention.getJSONObject("user");
							System.out.println(user.get("screen_name")+" hat dich in einem tweet erwähnt:");
							System.out.println("\t"+mention.get("text"));
							String status = Mention.generateAnswer((String)user.get("screen_name"), (String)mention.get("text"));
							System.out.println("Deine Antwort: "+status);
							Status.setStatus(service, accessToken, status);
							
							lastMentionId = (Long) mention.get("id");
						}
					} catch (JSONException e) {
						System.out.println("Fehler beim überprüfen auf neue Erwähnungen. Beschädigte Antwort vom Server.");
					}
					timestamp = System.currentTimeMillis();
				}
			} // end while(true)
		} catch (CriticalErrorException e) {
			System.out.println(e.getMessage());
		} catch (ConfigException e) {
			System.out.println(e.getMessage());
		}
	} // end main()
}
