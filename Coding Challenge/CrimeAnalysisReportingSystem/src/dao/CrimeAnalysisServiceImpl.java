package dao;
import java.sql.Connection;

import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.SQLIntegrityConstraintViolationException;



import com.hexaware.myexceptions.*;

import entity.Incident;
import entity.Report;
import util.DBConnUtil;

public class CrimeAnalysisServiceImpl implements ICrimeAnalysisService{

	 private static Connection connection;
	 public CrimeAnalysisServiceImpl() {
	        connection = DBConnUtil.getConnection();
	    }
	 
	 @Override
	 public boolean createIncident(Incident incident) throws OfficerNotFoundException, VictimNotFoundException, SuspectNotFoundException {
	     String query = "INSER"
	     		+ "T INTO Incidents (incidentType, incidentDate, location, description, status, victimId, suspectId, officerId) " +
	                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
	     
	     try (PreparedStatement pstmt = connection.prepareStatement(query)) {
	         pstmt.setString(1, incident.getIncidentType());
	         pstmt.setDate(2, new java.sql.Date(incident.getIncidentDate().getTime()));
	         pstmt.setString(3, incident.getLocation());
	         pstmt.setString(4, incident.getDescription());
	         pstmt.setString(5, incident.getStatus());
	         pstmt.setInt(6, incident.getVictimId());
	         pstmt.setInt(7, incident.getSuspectId());
	         pstmt.setInt(8, incident.getOfficerId());

	         int rows = pstmt.executeUpdate();
	         return rows > 0;

	     } catch (SQLIntegrityConstraintViolationException e) {
	         String msg = e.getMessage().toLowerCase();
	         if (msg.contains("officerid")) {
	             throw new OfficerNotFoundException("Officer ID " + incident.getOfficerId() + " does not exist.");
	         } else if (msg.contains("victimid")) {
	             throw new VictimNotFoundException("Victim ID " + incident.getVictimId() + " does not exist.");
	         } else if (msg.contains("suspectid")) {
	             throw new SuspectNotFoundException("Suspect ID " + incident.getSuspectId() + " does not exist.");
	         } else {
	             System.out.println("Foreign key constraint violation: " + e.getMessage());
	             return false;
	         }

	     } catch (SQLException e) {
	         System.out.println("Error in createIncident: " + e.getMessage());
	         return false;
	     }
	 }

	 
	 @Override
	 public boolean updateIncidentStatus(String status, int incidentId) throws IncidentNumberNotFoundException {
	     // Step 1: Check if the incident ID exists
	     String checkQuery = "SELECT IncidentID FROM Incidents WHERE IncidentID = ?";
	     String updateQuery = "UPDATE Incidents SET status = ? WHERE incidentId = ?";

	     try (
	         PreparedStatement checkStmt = connection.prepareStatement(checkQuery);
	         PreparedStatement updateStmt = connection.prepareStatement(updateQuery)
	     ) {
	         checkStmt.setInt(1, incidentId);
	         ResultSet rs = checkStmt.executeQuery();

	         if (!rs.next()) {
	             throw new IncidentNumberNotFoundException("Incident ID " + incidentId + " not found in the database.");
	         }
	         updateStmt.setString(1, status);
	         updateStmt.setInt(2, incidentId);

	         int rows = updateStmt.executeUpdate();
	         return rows > 0;

	     } catch (SQLException e) {
	         System.out.println("Error in updateIncidentStatus: " + e.getMessage());
	         return false;
	     }
	 }


	 
	 	@Override
	 	public List<Incident> getIncidentsInDateRange(Date startDate, Date endDate) {
	        List<Incident> incidents = new ArrayList<>();
	        String query = "SELECT * FROM Incidents WHERE incidentDate BETWEEN ? AND ?";

	        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
	            pstmt.setDate(1, new java.sql.Date(startDate.getTime()));
	            pstmt.setDate(2, new java.sql.Date(endDate.getTime()));

	            ResultSet rs = pstmt.executeQuery();

	            while (rs.next()) {
	                Incident i = new Incident(
	                    rs.getInt("incidentId"),
	                    rs.getString("incidentType"),
	                    rs.getDate("incidentDate"),
	                    rs.getString("location"),
	                    rs.getString("description"),
	                    rs.getString("status"),
	                    rs.getInt("victimId"),
	                    rs.getInt("suspectId"),
	                    rs.getInt("officerId")
	                );
	                incidents.add(i);
	            }

	        } catch (SQLException e) {
	            System.out.println("Error in getIncidentsInDateRange: " + e.getMessage());
	        }

	        return incidents;
	    }
	 
	 @Override
	    public List<Incident> searchIncidents(String incidentType) {
	        List<Incident> incidents = new ArrayList<>();
	        String query = "SELECT * FROM Incidents WHERE LOWER(incidentType) = LOWER(?)";

	        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
	            pstmt.setString(1, incidentType);
	            ResultSet rs = pstmt.executeQuery();

	            while (rs.next()) {
	                Incident i = new Incident(
	                    rs.getInt("incidentId"),
	                    rs.getString("incidentType"),
	                    rs.getDate("incidentDate"),
	                    rs.getString("location"),
	                    rs.getString("description"),
	                    rs.getString("status"),
	                    rs.getInt("victimId"),
	                    rs.getInt("suspectId"),
	                    rs.getInt("officerId")
	                );
	                incidents.add(i);
	            }

	        } catch (SQLException e) {
	            System.out.println("Error in searchIncidents: " + e.getMessage());
	        }

	        return incidents;
	    }

	 @Override
	 public Incident getIncidentById(int incidentId) throws IncidentNumberNotFoundException {
	     String query = "SELECT * FROM Incidents WHERE incidentId = ?";
	     try (PreparedStatement pstmt = connection.prepareStatement(query)) {
	         pstmt.setInt(1, incidentId);
	         ResultSet rs = pstmt.executeQuery();
	         if (rs.next()) {
	             return new Incident(
	                 rs.getInt("incidentId"),
	                 rs.getString("incidentType"),
	                 rs.getDate("incidentDate"),
	                 rs.getString("location"),
	                 rs.getString("description"),
	                 rs.getString("status"),
	                 rs.getInt("victimId"),
	                 rs.getInt("suspectId"),
	                 rs.getInt("officerId")
	             );
	         } else {
	             throw new IncidentNumberNotFoundException("Incident ID not found: " + incidentId);
	         }
	     } catch (SQLException e) {
	         System.out.println("Error in getIncidentById: " + e.getMessage());
	         throw new IncidentNumberNotFoundException("DB Error fetching Incident ID: " + incidentId);
	     }
	 }

	    @Override
	    public Report generateIncidentReport(Incident incident) {
	        Report report = new Report();
	        report.setIncidentId(incident.getIncidentId());
	        report.setReportDate(new java.util.Date());
	        report.setReportDetails("\n   Report for Incident ID: " + incident.getIncidentId() + ",\n   Type: " + incident.getIncidentType()+" \n   Description: "+incident.getDescription());
	        report.setStatus(incident.getStatus());
	        report.setReportingOfficerId(incident.getOfficerId());

	        return report;
	    }


}
