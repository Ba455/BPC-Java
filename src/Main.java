
import clinic.*;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static ClinicSystem clinic = new ClinicSystem();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // Initialize the system with sample data
        clinic.initialize();

        boolean running = true;
        while (running) {
            displayMainMenu();
            int choice = getIntInput("Enter your choice: ");

            switch (choice) {
                case 1:
                    patientManagementMenu();
                    break;
                case 2:
                    bookingMenu();
                    break;
                case 3:
                    appointmentManagementMenu();
                    break;
                case 4:
                    generateReport();
                    break;
                case 0:
                    running = false;
                    System.out.println("Thank you for using BPC Booking System. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void displayMainMenu() {
        System.out.println("\n===== BOOST PHYSIO CLINIC BOOKING SYSTEM =====");
        System.out.println("1. Patient Management");
        System.out.println("2. Appointment Booking");
        System.out.println("3. Appointment Management");
        System.out.println("4. Generate Reports");
        System.out.println("0. Exit");
    }

    private static void patientManagementMenu() {
        boolean subMenuRunning = true;
        while (subMenuRunning) {
            System.out.println("\n----- Patient Management -----");
            System.out.println("1. Add a new patient");
            System.out.println("2. Remove a patient");
            System.out.println("3. List all patients");
            System.out.println("0. Back to main menu");

            int choice = getIntInput("Enter your choice: ");

            switch (choice) {
                case 1:
                    addPatient();
                    break;
                case 2:
                    removePatient();
                    break;
                case 3:
                    listAllPatients();
                    break;
                case 0:
                    subMenuRunning = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void addPatient() {
        System.out.println("\n----- Add New Patient -----");
        System.out.print("Enter full name: ");
        String name = scanner.nextLine();

        System.out.print("Enter address: ");
        String address = scanner.nextLine();

        System.out.print("Enter telephone number: ");
        String telephone = scanner.nextLine();

        clinic.addPatient(name, address, telephone);
    }

    private static void removePatient() {
        System.out.println("\n----- Remove Patient -----");
        listAllPatients();

        int patientId = getIntInput("Enter patient ID to remove (0 to cancel): ");
        if (patientId != 0) {
            clinic.removePatient(patientId);
        }
    }

    private static void listAllPatients() {
        System.out.println("\n----- All Patients -----");
        List<Patient> patients = clinic.getPatients();
        if (patients.isEmpty()) {
            System.out.println("No patients registered in the system.");
        } else {
            System.out.printf("%-5s %-25s %-35s %-15s\n", "ID", "Name", "Address", "Telephone");
            System.out.println("--------------------------------------------------------------------------");
            for (Patient patient : patients) {
                System.out.printf("%-5d %-25s %-35s %-15s\n",
                        patient.getId(),
                        patient.getFullName(),
                        patient.getAddress(),
                        patient.getTelephone());
            }
        }
    }

    private static void bookingMenu() {
        boolean subMenuRunning = true;
        while (subMenuRunning) {
            System.out.println("\n----- Appointment Booking -----");
            System.out.println("1. Find appointments by expertise area");
            System.out.println("2. Find appointments by physiotherapist");
            System.out.println("0. Back to main menu");

            int choice = getIntInput("Enter your choice: ");

            switch (choice) {
                case 1:
                    bookByExpertise();
                    break;
                case 2:
                    bookByPhysiotherapist();
                    break;
                case 0:
                    subMenuRunning = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void bookByExpertise() {
        System.out.println("\n----- Book by Expertise Area -----");

        // List all expertise areas
        List<String> expertiseAreas = clinic.getAllExpertiseAreas();
        System.out.println("Available expertise areas:");
        for (int i = 0; i < expertiseAreas.size(); i++) {
            System.out.println((i + 1) + ". " + expertiseAreas.get(i));
        }

        int areaChoice = getIntInput("Select an expertise area (0 to cancel): ");
        if (areaChoice < 1 || areaChoice > expertiseAreas.size()) {
            return;
        }

        String selectedExpertise = expertiseAreas.get(areaChoice - 1);
        List<AvailableAppointment> availableAppointments = clinic.findAppointmentsByExpertise(selectedExpertise);

        bookAppointment(availableAppointments);
    }

    private static void bookByPhysiotherapist() {
        System.out.println("\n----- Book by Physiotherapist -----");

        // List all physiotherapists
        List<Physiotherapist> physios = clinic.getPhysiotherapists();
        System.out.println("Available physiotherapists:");
        for (int i = 0; i < physios.size(); i++) {
            System.out.println((i + 1) + ". " + physios.get(i).getFullName() + " - Expertise: "
                    + String.join(", ", physios.get(i).getExpertiseAreas()));
        }

        int physioChoice = getIntInput("Select a physiotherapist (0 to cancel): ");
        if (physioChoice < 1 || physioChoice > physios.size()) {
            return;
        }

        String selectedPhysio = physios.get(physioChoice - 1).getFullName();
        List<AvailableAppointment> availableAppointments = clinic.findAppointmentsByPhysiotherapist(selectedPhysio);

        bookAppointment(availableAppointments);
    }

    private static void bookAppointment(List<AvailableAppointment> availableAppointments) {
        if (availableAppointments.isEmpty()) {
            System.out.println("No available appointments found.");
            return;
        }

        System.out.println("\nAvailable appointments:");
        for (int i = 0; i < availableAppointments.size(); i++) {
            AvailableAppointment apt = availableAppointments.get(i);
            System.out.println((i + 1) + ". " + apt.getTreatment().getName()
                    + " with " + apt.getPhysiotherapist().getFullName()
                    + " at " + apt.getTimeSlot().getFormattedTimeRange());
        }

        int appointmentChoice = getIntInput("Select an appointment (0 to cancel): ");
        if (appointmentChoice < 1 || appointmentChoice > availableAppointments.size()) {
            return;
        }

        // List all patients to select one for booking
        System.out.println("\nSelect a patient for this appointment:");
        listAllPatients();

        int patientId = getIntInput("Enter patient ID (0 to cancel): ");
        if (patientId == 0) {
            return;
        }

        AvailableAppointment selectedAppointment = availableAppointments.get(appointmentChoice - 1);
        clinic.bookAppointment(patientId, selectedAppointment);
    }

    private static void appointmentManagementMenu() {
        boolean subMenuRunning = true;
        while (subMenuRunning) {
            System.out.println("\n----- Appointment Management -----");
            System.out.println("1. View patient appointments");
            System.out.println("2. Cancel an appointment");
            System.out.println("3. Check in for an appointment");
            System.out.println("0. Back to main menu");

            int choice = getIntInput("Enter your choice: ");

            switch (choice) {
                case 1:
                    viewPatientAppointments();
                    break;
                case 2:
                    cancelAppointment();
                    break;
                case 3:
                    checkInAppointment();
                    break;
                case 0:
                    subMenuRunning = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void viewPatientAppointments() {
        System.out.println("\n----- View Patient Appointments -----");
        listAllPatients();

        int patientId = getIntInput("Enter patient ID (0 to cancel): ");
        if (patientId == 0) {
            return;
        }

        Patient patient = clinic.getPatientById(patientId);
        if (patient == null) {
            System.out.println("Patient not found with ID: " + patientId);
            return;
        }

        List<Appointment> appointments = patient.getAppointments();
        if (appointments.isEmpty()) {
            System.out.println("No appointments found for " + patient.getFullName());
        } else {
            System.out.println("\nAppointments for " + patient.getFullName() + ":");
            System.out.printf("%-5s %-20s %-20s %-30s %-10s\n",
                    "ID", "Treatment", "Physiotherapist", "Time", "Status");
            System.out.println("--------------------------------------------------------------------------------------");

            for (Appointment apt : appointments) {
                System.out.printf("%-5d %-20s %-20s %-30s %-10s\n",
                        apt.getId(),
                        apt.getTreatment().getName(),
                        apt.getPhysiotherapist().getFullName(),
                        apt.getTimeSlot().getFormattedTimeRange(),
                        apt.getStatus());
            }
        }
    }

    private static void cancelAppointment() {
        System.out.println("\n----- Cancel Appointment -----");
        listAllPatients();

        int patientId = getIntInput("Enter patient ID (0 to cancel): ");
        if (patientId == 0) {
            return;
        }

        Patient patient = clinic.getPatientById(patientId);
        if (patient == null) {
            System.out.println("Patient not found with ID: " + patientId);
            return;
        }

        List<Appointment> futureAppointments = patient.getFutureAppointments();
        if (futureAppointments.isEmpty()) {
            System.out.println("No future appointments found for " + patient.getFullName());
            return;
        }

        System.out.println("\nFuture appointments for " + patient.getFullName() + ":");
        System.out.printf("%-5s %-20s %-20s %-30s\n",
                "ID", "Treatment", "Physiotherapist", "Time");
        System.out.println("-------------------------------------------------------------------------");

        for (Appointment apt : futureAppointments) {
            System.out.printf("%-5d %-20s %-20s %-30s\n",
                    apt.getId(),
                    apt.getTreatment().getName(),
                    apt.getPhysiotherapist().getFullName(),
                    apt.getTimeSlot().getFormattedTimeRange());
        }

        int appointmentId = getIntInput("Enter appointment ID to cancel (0 to cancel): ");
        if (appointmentId != 0) {
            clinic.cancelAppointment(patientId, appointmentId);
        }
    }

    private static void checkInAppointment() {
        System.out.println("\n----- Check In Appointment -----");
        listAllPatients();

        int patientId = getIntInput("Enter patient ID (0 to cancel): ");
        if (patientId == 0) {
            return;
        }

        Patient patient = clinic.getPatientById(patientId);
        if (patient == null) {
            System.out.println("Patient not found with ID: " + patientId);
            return;
        }

        List<Appointment> futureAppointments = patient.getFutureAppointments();
        if (futureAppointments.isEmpty()) {
            System.out.println("No future appointments found for " + patient.getFullName());
            return;
        }

        System.out.println("\nFuture appointments for " + patient.getFullName() + ":");
        System.out.printf("%-5s %-20s %-20s %-30s\n",
                "ID", "Treatment", "Physiotherapist", "Time");
        System.out.println("-------------------------------------------------------------------------");

        for (Appointment apt : futureAppointments) {
            System.out.printf("%-5d %-20s %-20s %-30s\n",
                    apt.getId(),
                    apt.getTreatment().getName(),
                    apt.getPhysiotherapist().getFullName(),
                    apt.getTimeSlot().getFormattedTimeRange());
        }

        int appointmentId = getIntInput("Enter appointment ID to check in (0 to cancel): ");
        if (appointmentId != 0) {
            clinic.checkInAppointment(appointmentId);
        }
    }

    private static void generateReport() {
        clinic.generateReport();
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }

    private static int getIntInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                String input = scanner.nextLine();
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }
}
