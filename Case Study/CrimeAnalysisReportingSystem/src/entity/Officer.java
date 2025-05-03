package entity;

public class Officer {
    private int officerId;
    private String firstName;
    private String lastName;
    private String badgeNumber;
    private String rank;
    private String contactInfo;
    private int agencyId;

    public Officer() {}

    public Officer(int officerId, String firstName, String lastName, String badgeNumber,
                   String rank, String contactInfo, int agencyId) {
        this.officerId = officerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.badgeNumber = badgeNumber;
        this.rank = rank;
        this.contactInfo = contactInfo;
        this.agencyId = agencyId;
    }

    // Getters and Setters
    public int getOfficerId() {
        return officerId;
    }

    public void setOfficerId(int officerId) {
        this.officerId = officerId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBadgeNumber() {
        return badgeNumber;
    }

    public void setBadgeNumber(String badgeNumber) {
        this.badgeNumber = badgeNumber;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public int getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(int agencyId) {
        this.agencyId = agencyId;
    }
}
