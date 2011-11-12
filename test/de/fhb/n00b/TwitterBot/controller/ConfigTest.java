package de.fhb.n00b.TwitterBot.controller;

import static org.junit.Assert.*;

import org.junit.Test;
import org.scribe.model.Token;

import de.fhb.n00b.TwitterBot.exception.ConfigException;

public class ConfigTest {

	@Test
	public void testWriteAndRead() {
		Token writeToken = new Token("foo","bar");
		Token readToken = null;
		try {
			Config.writeConfig(writeToken);
			readToken = Config.readConfig();
		} catch (ConfigException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		assertEquals("ausgabe O.o", writeToken.toString(), readToken.toString());
	}
}
