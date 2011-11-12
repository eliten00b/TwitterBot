package de.fhb.n00b.TwitterBot.controller;

import org.scribe.model.*;
import org.scribe.oauth.*;

public class Mention {

	public static Response getLastMention(OAuthService service, Token accessToken) {
		
		OAuthRequest request = new OAuthRequest(Verb.GET, "https://api.twitter.com/1/statuses/mentions.json?count=1");
		service.signRequest(accessToken, request);
		return request.send();
	}

	public static Response getMentionsSince(OAuthService service, Token accessToken, long since_id) {
		
		OAuthRequest request = new OAuthRequest(Verb.GET, "https://api.twitter.com/1/statuses/mentions.json?since_id="+since_id);
		service.signRequest(accessToken, request);
		return request.send();
	}
	
	public static String generateAnswer(String toUser, String mention) {
		
		String answer = "@"+toUser+" ";
		if (mention.indexOf("wie geht es dir?") > -1) answer += "ganz gut soweit, hab 5 Punkte in SQ bekommen.";
		else if (mention.indexOf("Lost") > -1) answer += "4 8 15 16 23 42";
		else if (mention.indexOf("Kuchen") > -1) answer += "the cake is a lie!";
		else if (mention.indexOf("PI") > -1) answer += "3,14159 26535 89793 23846 26433 83279 50288 41971 69399 37510 58209 74944 59230 78164 06286 20899 86280 34825 34211 70679";
		else if (mention.endsWith("?")) answer += "na 42^^";
		else answer += "der Computer sagt 'Nein'";
		return answer;
	}
}
