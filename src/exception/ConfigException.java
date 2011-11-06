package exception;

public class ConfigException extends Exception {

	private static final long serialVersionUID = 1511274629570910512L;

	public ConfigException() {
		super("Login fehlgeschlagen. Bitte Setup ausführen. Anwendung mit Parameter -setup starten.");
	}
	
	public ConfigException(String str){
		super(str);
	}
}
