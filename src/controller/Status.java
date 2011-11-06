package controller;

import org.scribe.model.*;
import org.scribe.oauth.*;

public class Status {

	public static Response setStatus(OAuthService service, Token accessToken, String status) {
		
		OAuthRequest request = new OAuthRequest(Verb.POST, "https://api.twitter.com/1/statuses/update.json");
		request.addBodyParameter("status", status);
		service.signRequest(accessToken, request);
		return request.send();
	}
}
