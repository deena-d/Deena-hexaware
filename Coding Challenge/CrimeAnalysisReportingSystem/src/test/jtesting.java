package test;

import static org.junit.jupiter.api.Assertions.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.hexaware.myexceptions.IncidentNumberNotFoundException;
import com.hexaware.myexceptions.OfficerNotFoundException;
import com.hexaware.myexceptions.SuspectNotFoundException;
import com.hexaware.myexceptions.VictimNotFoundException;

import dao.CrimeAnalysisServiceImpl;
import entity.Incident;
import entity.Report;

public class jtesting {

    static CrimeAnalysisServiceImpl service;

    @BeforeAll
    public static void init() {
        service = new CrimeAnalysisServiceImpl();
    }

    @Test
    void testCreateIncident_Success() {
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse("2023-04-10");
            Incident incident = new Incident(
                901,                        
                "Robbery",
                date,
                "13.0102,80.2210",
                "Gold chain stolen",
                "Open",
                1,                       
                2,                      
                3                        
            );
            boolean result = service.createIncident(incident);
            assertTrue(result, "Incident should be inserted successfully");
        } catch (Exception e) {
            fail("Unexpected exception during createIncident: " + e.getMessage());
        }
    }

    @Test
    void testGetIncidentById_ValidId() {
        try {
            Incident incident = service.getIncidentById(4); 
            assertNotNull(incident);
            assertEquals("Closed", incident.getStatus());
        } catch (IncidentNumberNotFoundException e) {
            fail("Incident not found with valid ID");
        }
    }

    @Test
    void testGetIncidentById_InvalidId() {
        assertThrows(IncidentNumberNotFoundException.class, () -> {
            service.getIncidentById(9999); 
        });
    }

    @Test
    void testUpdateIncidentStatus_ValidUpdate() {
        try {
            boolean updated = service.updateIncidentStatus("Closed", 4); 
            assertTrue(updated);
            Incident updatedIncident = service.getIncidentById(4);
            assertEquals("Closed", updatedIncident.getStatus());
        } catch (IncidentNumberNotFoundException e) {
            fail("Failed to update existing incident");
        }
    }

    @Test
    void testUpdateIncidentStatus_InvalidIncidentId() {
        assertThrows(IncidentNumberNotFoundException.class, () -> {
            service.updateIncidentStatus("Closed", 8888); 
        });
    }
	

    @Test
    void testGetIncidentsInDateRange() {
        try {
            Date start = new SimpleDateFormat("yyyy-MM-dd").parse("2023-01-01");
            Date end = new SimpleDateFormat("yyyy-MM-dd").parse("2023-12-31");

            List<Incident> incidents = service.getIncidentsInDateRange(start, end);
            assertNotNull(incidents);
            assertFalse(incidents.isEmpty(), "Incident list should not be empty.");
        } catch (Exception e) {
            fail("Exception in date range test: " + e.getMessage());
        }
    }

    @Test
    void testSearchIncidentsByType() {
        List<Incident> incidents = service.searchIncidents("Robbery");
        assertNotNull(incidents);
        assertTrue(incidents.size() > 0, "Expected incidents of type 'Robbery'");
    }

    @Test
    void testGenerateIncidentReport() {
        try {
            Incident incident = service.getIncidentById(2); 
            Report report = service.generateIncidentReport(incident);
            assertNotNull(report);
            assertEquals(incident.getIncidentId(), report.getIncidentId());
            assertEquals(incident.getStatus(), report.getStatus());
        } catch (IncidentNumberNotFoundException e) {
            fail("Incident not found for report generation.");
        }
    }
}
