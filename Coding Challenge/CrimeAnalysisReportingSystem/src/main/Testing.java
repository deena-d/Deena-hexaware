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

public class Testing {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ICrimeAnalysisService service = new CrimeAnalysisServiceImpl();

        SimpleDateFormat inputFormat = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy");

        boolean exit = false;

        while (!exit) {
            System.out.println("\n=== CRIME ANALYSIS & REPORTING SYSTEM ===");
            System.out.println("1. Create New Incident");
            System.out.println("2. Update Incident Status");
            System.out.println("3. Search Incident by Type");
            System.out.println("4. Get Incidents in Date Range");
            System.out.println("5. Generate Report for an Incident");
            System.out.println("0. Exit");
            System.out.print("Choose an option: ");

            String choice = sc.nextLine();

            try {
                switch (choice) {
                case "1":
                    try {
                        System.out.print("Enter Incident Type: ");
                        String type = FormatUtil.toTitleCase(sc.nextLine());

                        System.out.print("Enter Incident Date (dd-MM-yyyy): ");
                        Date incidentDate = inputFormat.parse(sc.nextLine());

                        System.out.print("Enter Location: ");
                        String location = FormatUtil.toTitleCase(sc.nextLine());

                        System.out.print("Enter Description: ");
                        String description = FormatUtil.toTitleCase(sc.nextLine());

                        System.out.print("Enter Status: ");
                        String status = FormatUtil.toTitleCase(sc.nextLine());

                        System.out.print("Enter Victim ID: ");
                        int victimId = Integer.parseInt(sc.nextLine());

                        System.out.print("Enter Suspect ID: ");
                        int suspectId = Integer.parseInt(sc.nextLine());

                        System.out.print("Enter Officer ID: ");
                        int officerId = Integer.parseInt(sc.nextLine());

                        Incident incident = new Incident(0, type, incidentDate, location, description, status, victimId, suspectId, officerId);

                        boolean isCreated = service.createIncident(incident);
                        System.out.println(isCreated ? "✅ Incident Created Successfully" : "❌ Failed to Create Incident");

                    } catch (VictimNotFoundException | OfficerNotFoundException | SuspectNotFoundException e) {
                        System.out.println("❌ " + e.getMessage());
                    } catch (Exception e) {
                        System.out.println("❌ Unexpected Error: " + e.getMessage());
                    }
                    break;


                    case "2":
                        System.out.print("Enter Incident ID to update: ");
                        int upId = Integer.parseInt(sc.nextLine());

                        System.out.print("Enter New Status: ");
                        String newStatus = FormatUtil.toTitleCase(sc.nextLine());

                        boolean isUpdated = service.updateIncidentStatus(newStatus, upId);
                        System.out.println(isUpdated ? "✅ Status Updated" : "❌ Failed to Update Status");
                        break;

                    case "3":
                        System.out.print("Enter Incident Type to Search: ");
                        String searchType = FormatUtil.toTitleCase(sc.nextLine());

                        List<Incident> foundIncidents = service.searchIncidents(searchType);
                        System.out.println("🔍 Found " + foundIncidents.size() + " incident(s):");
                        for (Incident inc : foundIncidents) {
                            System.out.println(" - ID: " + inc.getIncidentId() + ", Date: " + outputFormat.format(inc.getIncidentDate()) + ", Location: " + inc.getLocation());
                        }
                        break;

                    case "4":
                        System.out.print("Enter Start Date (dd-MM-yyyy): ");
                        Date startDate = inputFormat.parse(sc.nextLine());

                        System.out.print("Enter End Date (dd-MM-yyyy): ");
                        Date endDate = inputFormat.parse(sc.nextLine());

                        List<Incident> rangeIncidents = service.getIncidentsInDateRange(startDate, endDate);
                        System.out.println("📅 Incidents found: " + rangeIncidents.size());
                        for (Incident inc : rangeIncidents) {
                            System.out.println(" - ID: " + inc.getIncidentId() + ", Type: " + inc.getIncidentType() + ", Date: " + outputFormat.format(inc.getIncidentDate()));
                        }
                        break;

                    case "5":
                        System.out.print("Enter Incident ID for Report Generation: ");
                        int reportIncidentId = Integer.parseInt(sc.nextLine());

                        try {
                            Incident incidentToReport = service.getIncidentById(reportIncidentId);
                            Report report = service.generateIncidentReport(incidentToReport);

                            System.out.println("📝 Report Generated:");
                            System.out.println(" - Incident ID: " + report.getIncidentId());
                            System.out.println(" - Report Date: " + outputFormat.format(report.getReportDate()));
                            System.out.println(" - Details: " + report.getReportDetails());
                            System.out.println(" - Status: " + report.getStatus());

                        } catch (IncidentNumberNotFoundException e) {
                            System.out.println("❌ " + e.getMessage());
                        }

                        break;

                    case "0":
                        exit = true;
                        System.out.println("👋 Exiting Application. Goodbye!");
                        break;

                    default:
                        System.out.println("⚠️ Invalid option! Please try again.");
                }

            } catch (Exception e) {
                System.out.println("❌ Error: " + e.getMessage());
            }
        }

        sc.close();
    }
}
