package de.fhb.n00b.TwitterBot.exception;

public class CriticalErrorException extends Exception {

	private static final long serialVersionUID = -6118668895371900922L;

	public CriticalErrorException(String str){
		super(str);
	}
}
