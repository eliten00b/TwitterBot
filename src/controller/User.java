package controller;

import org.scribe.model.*;
import org.scribe.oauth.*;

public class User {

	public static Response loginUser(OAuthService service, Token accessToken) {
		
		OAuthRequest request = new OAuthRequest(Verb.GET, "http://api.twitter.com/1/account/verify_credentials.json");
		service.signRequest(accessToken, request);
		return request.send();
	}
}
