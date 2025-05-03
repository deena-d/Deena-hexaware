package entity;

public class Evidence {
    private int evidenceId;
    private String description;
    private String locationFound;
    private int incidentId;

    public Evidence() {}

    public Evidence(int evidenceId, String description, String locationFound, int incidentId) {
        this.evidenceId = evidenceId;
        this.description = description;
        this.locationFound = locationFound;
        this.incidentId = incidentId;
    }

    // Getters and Setters
    public int getEvidenceId() {
        return evidenceId;
    }

    public void setEvidenceId(int evidenceId) {
        this.evidenceId = evidenceId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocationFound() {
        return locationFound;
    }

    public void setLocationFound(String locationFound) {
        this.locationFound = locationFound;
    }

    public int getIncidentId() {
        return incidentId;
    }

    public void setIncidentId(int incidentId) {
        this.incidentId = incidentId;
    }
}
