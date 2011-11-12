package de.fhb.n00b.TwitterBot.controller;

import java.io.*;
import java.util.Scanner;

import org.scribe.model.*;
import org.scribe.oauth.*;

import de.fhb.n00b.TwitterBot.exception.*;


public class Config {

	public static Token runSetup(OAuthService service) {
		
		Scanner in = new Scanner(System.in);
		Token requestToken = service.getRequestToken();
		System.out.println(" -=Setup=- ");
	    System.out.println("Bitte gehe auf diese Seite, autorisiere die Anwendung und gib den Code hier ein:");
	    System.out.println(service.getAuthorizationUrl(requestToken));
	    System.out.print("Code: ");
	    Verifier verifier = new Verifier(in.nextLine());
	    System.out.println();
		return service.getAccessToken(requestToken, verifier);
	}
	
	public static void writeConfig(Token token) throws ConfigException {
		
		File file = new File("twitterbot.conf");
	     try {
	       FileWriter writer = new FileWriter(file, false);
	       writer.write(token.getToken());
	       writer.write(System.getProperty("line.separator"));
	       writer.write(token.getSecret());
	       writer.flush();
	       writer.close();
		} catch (IOException e) {
			throw new ConfigException("Fehler beim schreiben der Konfigurationsdatei. Bitte Rechte überprüfen.");
		}
	}
	
	public static Token readConfig() throws ConfigException {
		try {
		    BufferedReader br = new BufferedReader(new FileReader("twitterbot.conf"));
		    try {
		    	String token = br.readLine();
		    	String secret = br.readLine();
		    	if (token == null || secret == null) {
		    		throw new ConfigException("Konfigurationsdatei unvollständig. Bitte Setup neu ausführen.");
		    	}
		    	return new Token(token,secret);
		    } catch (IOException e) {
		    	throw new ConfigException("Fehler beim lesen der Konfigurationsdatei.");
		    }
	    } catch (FileNotFoundException e) {
	    	return null;
	    }
	}
}
