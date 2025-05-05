package exception;
public class PatientNumberNotFoundException extends Exception {
	
private static final long serialVersionUID = 1L;
	public PatientNumberNotFoundException() {
        super("Patient number not found in the database.");
    }

    public PatientNumberNotFoundException(String message) {
        super(message);
    }
}
