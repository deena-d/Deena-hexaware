package dao;

import entity.Incident; 
import entity.Report;

import java.util.Date;
import java.util.List;
import com.hexaware.myexceptions.*;

public interface ICrimeAnalysisService {

    boolean createIncident(Incident incident) throws OfficerNotFoundException, VictimNotFoundException, SuspectNotFoundException;

    boolean updateIncidentStatus(String status, int incidentId) throws IncidentNumberNotFoundException;

    List<Incident> getIncidentsInDateRange(Date startDate, Date endDate);

    List<Incident> searchIncidents(String incidentType);

    Report generateIncidentReport(Incident incident);
    
    Incident getIncidentById(int incidentId) throws IncidentNumberNotFoundException;

    
    
}