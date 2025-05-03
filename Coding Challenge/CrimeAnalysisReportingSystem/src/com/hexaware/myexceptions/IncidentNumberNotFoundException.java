package com.hexaware.myexceptions;

public class IncidentNumberNotFoundException extends Exception {
	private static final long serialVersionUID = 1L;
    public IncidentNumberNotFoundException() {
        super("Incident with the given ID was not found in the database.");
    }

    public IncidentNumberNotFoundException(String message) {
        super(message);
    }
}
