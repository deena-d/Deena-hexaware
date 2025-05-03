package main;

import dao.CrimeAnalysisServiceImpl;
import dao.ICrimeAnalysisService;
import entity.Incident;
import entity.Report;
import com.hexaware.myexceptions.*;
import util.FormatUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class MainModule {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ICrimeAnalysisService service = new CrimeAnalysisServiceImpl();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        boolean exit = false;
 
        while (!exit) {
        	System.out.println("===============================================");
        	System.out.println("       CRIME ANALYSIS & REPORTING SYSTEM       ");
        	System.out.println("===============================================");
        	System.out.println("\n-------------- MENU --------------");
            System.out.println("1. Create Incident");
            System.out.println("2. Update Incident Status");
            System.out.println("3. Search Incidents by Type");
            System.out.println("4. Get Incidents in Date Range");
            System.out.println("5. Generate Incident Report");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");

            String choice = scanner.nextLine();

            try {
                switch (choice) {
                    case "1":
                        try {
                            System.out.print("Incident Type: ");
                            String type = FormatUtil.toTitleCase(scanner.nextLine());

                            System.out.print("Incident Date (dd-MM-yyyy): ");
                            Date incidentDate = dateFormat.parse(scanner.nextLine());

                            System.out.print("Latitude: ");
                            double latitude = Double.parseDouble(scanner.nextLine());

                            System.out.print("Longitude: ");
                            double longitude = Double.parseDouble(scanner.nextLine());

                            String location = latitude + "," + longitude;

                            System.out.print("Description: ");
                            String description = FormatUtil.toTitleCase(scanner.nextLine());

                            System.out.print("Status: ");
                            String status = FormatUtil.toTitleCase(scanner.nextLine());

                            System.out.print("Victim ID: ");
                            int victimId = Integer.parseInt(scanner.nextLine());

                            System.out.print("Suspect ID: ");
                            int suspectId = Integer.parseInt(scanner.nextLine());

                            System.out.print("Officer ID: ");
                            int officerId = Integer.parseInt(scanner.nextLine());

                            Incident incident = new Incident(0, type, incidentDate, location, description, status, victimId, suspectId, officerId);
                            boolean created = service.createIncident(incident);

                            System.out.println("-----------------------------------------------");
                            if (created) {
                                System.out.println("Status: SUCCESS");
                                System.out.println("The incident was recorded successfully.");
                            } else {
                                System.out.println("Status: FAILURE");
                                System.out.println("Could not record the incident.");
                            }
                            System.out.println("-----------------------------------------------");
                        } catch (VictimNotFoundException | SuspectNotFoundException | OfficerNotFoundException e) {
                            System.out.println(e.getMessage());
                        }
                        break;

                    case "2":
                        System.out.print("Incident ID: ");
                        int updateId = Integer.parseInt(scanner.nextLine());

                        System.out.print("New Status: ");
                        String newStatus = FormatUtil.toTitleCase(scanner.nextLine());

                        boolean updated = service.updateIncidentStatus(newStatus, updateId);
                        System.out.println("-----------------------------------------------");
                        if (updated) {
                            System.out.println("-----------------------------------------------");
                            System.out.println("Status: SUCCESS");
                            System.out.println("Incident status updated successfully.");
                            System.out.println("-----------------------------------------------");
                        } else {
                            System.out.println("-----------------------------------------------");
                            System.out.println("Status: FAILURE");
                            System.out.println("Unable to update the incident status.");
                            System.out.println("-----------------------------------------------");
                        }

                        break;

                    case "3":
                        System.out.print("Incident Type: ");
                        String searchType = FormatUtil.toTitleCase(scanner.nextLine());

                        List<Incident> incidentsByType = service.searchIncidents(searchType);
                        if (incidentsByType.isEmpty()) {
                            System.out.println("No incidents found for the given type.");
                        } else {
                        	System.out.println("-----------------------------------------------");
                        	System.out.println("Status: SUCCESS");
                        	System.out.println("Total incidents found: " + incidentsByType.size());
                        	for (Incident inc : incidentsByType) {
                        	    System.out.println(" - ID: " + inc.getIncidentId() + ", Date: " + dateFormat.format(inc.getIncidentDate()) + ", Location: " + inc.getLocation());
                        	}
                        	System.out.println("-----------------------------------------------");

                        }
                        break;

                    case "4":
                        System.out.print("Start Date (dd-MM-yyyy): ");
                        Date startDate = dateFormat.parse(scanner.nextLine());

                        System.out.print("End Date (dd-MM-yyyy): ");
                        Date endDate = dateFormat.parse(scanner.nextLine());

                        List<Incident> incidentsInRange = service.getIncidentsInDateRange(startDate, endDate);
                        if (incidentsInRange.isEmpty()) {
                            System.out.println("No incidents found in the specified date range.");
                        } else {
                        	System.out.println("-----------------------------------------------");
                        	System.out.println("Status: SUCCESS");
                        	System.out.println("Total incidents in the selected date range: " + incidentsInRange.size());
                        	for (Incident inc : incidentsInRange) {
                        	    System.out.println(" - ID: " + inc.getIncidentId() + ", Type: " + inc.getIncidentType() + ", Date: " + dateFormat.format(inc.getIncidentDate()));
                        	}
                        	System.out.println("-----------------------------------------------");

                        }
                        break;

                    case "5":
                        System.out.print("Incident ID: ");
                        int reportId = Integer.parseInt(scanner.nextLine());

                        try {
                            Incident incident = service.getIncidentById(reportId);
                            Report report = service.generateIncidentReport(incident);
                            
                            System.out.println("\n--- Incident Details ---");
                            System.out.println("Incident ID   : " + report.getIncidentId());
                            System.out.println("Report Date   : " + dateFormat.format(report.getReportDate()));
                            System.out.println("Report Details: " + report.getReportDetails());
                            System.out.println("Status:         " + report.getStatus());
                        } catch (IncidentNumberNotFoundException e) {
                            System.out.println(e.getMessage());
                        }
                        break;

                    case "0":
                        exit = true;
                        System.out.println("\nApplication terminated. Thank you for using the system.");
                        System.out.println("-----------------------------------------------");
                        break;

                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        scanner.close();
    }
}
