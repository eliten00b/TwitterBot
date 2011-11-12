package de.fhb.n00b.TwitterBot.controller;

import static org.junit.Assert.*;
import org.junit.Test;

import de.fhb.n00b.TwitterBot.controller.Mention;

public class MentionTest {

	
	@Test
	public void testGenerateAnswer() {
		String answer = "";
		answer = Mention.generateAnswer("fruit", "@foo bar");
		assertEquals("Falsche Antwort.", "@fruit der Computer sagt 'Nein'", answer);
		answer = Mention.generateAnswer("fruit", "@foo hast du Freitag Lost gesehen?");
		assertEquals("Falsche Antwort.", "@fruit 4 8 15 16 23 42", answer);
		answer = Mention.generateAnswer("fruit", "@foo ich hab Kuchen^^");
		assertEquals("Falsche Antwort.", "@fruit the cake is a lie!", answer);
		answer = Mention.generateAnswer("fruit", "@foo O.o ?");
		assertEquals("Falsche Antwort.", "@fruit na 42^^", answer);
	}
}
