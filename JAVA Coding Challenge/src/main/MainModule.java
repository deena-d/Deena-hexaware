package main;
import dao.HospitalServiceImpl;
import dao.IHospitalService;
import entity.Appointment;
import java.util.List;
import java.util.Scanner;

public class MainModule {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        IHospitalService hospitalService = new HospitalServiceImpl();
        while (true) {
            System.out.println("\n=== HOSPITAL MANAGEMENT SYSTEM ===");
            System.out.println("1. Get Appointment by ID");
            System.out.println("2. Get Appointments for Patient");
            System.out.println("3. Get Appointments for Doctor");
            System.out.println("4. Schedule Appointment");
            System.out.println("5. Update Appointment");
            System.out.println("6. Cancel Appointment");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); 
            try {
                switch (choice) {
                    case 1:
                    {
                        System.out.print("Enter Appointment ID: ");
                        int id = scanner.nextInt();
                        Appointment app = hospitalService.getAppointmentById(id);
                        if (app != null) {
                            System.out.println(app);
                        } else {
                            System.out.println("Appointment not found.");
                        }
                        break;
                    }
                    case 2:
                    {
                        System.out.print("Enter Patient ID: ");
                        int pid = scanner.nextInt();
                        List<Appointment> list = hospitalService.getAppointmentsForPatient(pid);
                        list.forEach(System.out::println);
                        break;
                    }
                    case 3:
                    {
                        System.out.print("Enter Doctor ID: ");
                        int did = scanner.nextInt();
                        List<Appointment> list = hospitalService.getAppointmentsForDoctor(did);
                        list.forEach(System.out::println);
                        break;
                    }
                    case 4:
                    {
                        System.out.print("Enter Patient ID: ");
                        int pid = scanner.nextInt();
                        System.out.print("Enter Doctor ID: ");
                        int did = scanner.nextInt();
                        scanner.nextLine();
                        System.out.print("Enter Appointment Date (yyyy-mm-dd): ");
                        String date = scanner.nextLine();
                        System.out.print("Enter Description: ");
                        String desc = scanner.nextLine();

                        Appointment newApp = new Appointment(0, pid, did, date, desc);
                        boolean success = hospitalService.scheduleAppointment(newApp);
                        System.out.println(success ? "Appointment scheduled successfully." : "Failed to schedule appointment.");
                        break;
                    }
                    case 5:
                    {
                        System.out.print("Enter Appointment ID to update: ");
                        int aid = scanner.nextInt();
                        System.out.print("Enter Patient ID: ");
                        int pid = scanner.nextInt();
                        System.out.print("Enter Doctor ID: ");
                        int did = scanner.nextInt();
                        scanner.nextLine();
                        System.out.print("Enter Appointment Date (yyyy-mm-dd): ");
                        String date = scanner.nextLine();
                        System.out.print("Enter Description: ");
                        String desc = scanner.nextLine();

                        Appointment updatedApp = new Appointment(aid, pid, did, date, desc);
                        boolean updated = hospitalService.updateAppointment(updatedApp);
                        System.out.println(updated ? "Appointment updated." : "Update failed.");
                        break;
                    }
                    case 6:
                    {
                        System.out.print("Enter Appointment ID to cancel: ");
                        int aid = scanner.nextInt();
                        boolean cancelled = hospitalService.cancelAppointment(aid);
                        System.out.println(cancelled ? "Appointment cancelled." : "Cancel failed.");
                        break;
                    }
                    case 7:
                    {
                        System.out.println("Exiting application. Goodbye!");
                        scanner.close();
                        return;
                    }
                    default:
                    	System.out.println("Invalid choice. Please try again.");
                }
            } catch (Exception e) {
                System.out.println("Something went wrong: " + e.getMessage());
            }
        }
    }
} 


