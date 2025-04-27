package clinic;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ClinicSystem {

    private List<Physiotherapist> physiotherapists;
    private List<Patient> patients;
    private List<Treatment> treatments;
    private Timetable timetable;
    private int nextPersonId = 1000;
    private int nextTreatmentId = 1;
    private int nextAppointmentId = 10000;

    public ClinicSystem() {
        physiotherapists = new ArrayList<>();
        patients = new ArrayList<>();
        treatments = new ArrayList<>();
    }

    // Initialize the system with sample data
    public void initialize() {
        // Create treatments
        Treatment neuralMobilisation = new Treatment(nextTreatmentId++, "Neural mobilisation", "Physiotherapy");
        Treatment acupuncture = new Treatment(nextTreatmentId++, "Acupuncture", "Physiotherapy");
        Treatment massage = new Treatment(nextTreatmentId++, "Massage", "Physiotherapy");
        Treatment spineJointMobilisation = new Treatment(nextTreatmentId++, "Mobilisation of the spine and joints", "Osteopathy");
        Treatment poolRehabilitation = new Treatment(nextTreatmentId++, "Pool rehabilitation", "Rehabilitation");

        treatments.add(neuralMobilisation);
        treatments.add(acupuncture);
        treatments.add(massage);
        treatments.add(spineJointMobilisation);
        treatments.add(poolRehabilitation);

        // Create physiotherapists
        Physiotherapist sarah = new Physiotherapist(nextPersonId++, "Sarah Johnson", "123 Health St", "555-1234");
        sarah.addExpertise("Physiotherapy");
        sarah.addExpertise("Osteopathy");
        sarah.addTreatment(neuralMobilisation);
        sarah.addTreatment(acupuncture);
        sarah.addTreatment(massage);
        sarah.addTreatment(spineJointMobilisation);
        physiotherapists.add(sarah);

        Physiotherapist michael = new Physiotherapist(nextPersonId++, "Michael Wong", "456 Therapy Ave", "555-5678");
        michael.addExpertise("Rehabilitation");
        michael.addExpertise("Physiotherapy");
        michael.addTreatment(massage);
        michael.addTreatment(poolRehabilitation);
        physiotherapists.add(michael);

        Physiotherapist emma = new Physiotherapist(nextPersonId++, "Emma Chen", "789 Wellness Blvd", "555-9012");
        emma.addExpertise("Physiotherapy");
        emma.addExpertise("Rehabilitation");
        emma.addTreatment(acupuncture);
        emma.addTreatment(neuralMobilisation);
        emma.addTreatment(poolRehabilitation);
        physiotherapists.add(emma);

        Physiotherapist james = new Physiotherapist(nextPersonId++, "James Rodriguez", "321 Recovery Ln", "555-3456");
        james.addExpertise("Osteopathy");
        james.addTreatment(spineJointMobilisation);
        physiotherapists.add(james);

        // Create patients
        patients.add(new Patient(nextPersonId++, "John Smith", "12 Main St", "555-1111"));
        patients.add(new Patient(nextPersonId++, "Jane Doe", "34 Oak Ave", "555-2222"));
        patients.add(new Patient(nextPersonId++, "Robert Brown", "56 Pine Rd", "555-3333"));
        patients.add(new Patient(nextPersonId++, "Mary Williams", "78 Maple Dr", "555-4444"));
        patients.add(new Patient(nextPersonId++, "David Lee", "90 Cedar Ln", "555-5555"));
        patients.add(new Patient(nextPersonId++, "Lisa Garcia", "123 Elm St", "555-6666"));
        patients.add(new Patient(nextPersonId++, "Thomas Taylor", "456 Birch Ave", "555-7777"));
        patients.add(new Patient(nextPersonId++, "Patricia Martinez", "789 Spruce Rd", "555-8888"));
        patients.add(new Patient(nextPersonId++, "James Johnson", "12 Aspen Dr", "555-9999"));
        patients.add(new Patient(nextPersonId++, "Jennifer Wilson", "34 Redwood Ln", "555-0000"));
        patients.add(new Patient(nextPersonId++, "Charles Anderson", "56 Willow St", "555-1212"));
        patients.add(new Patient(nextPersonId++, "Elizabeth Thomas", "78 Fir Ave", "555-2323"));

        // Set up 4-week timetable (May 2025)
        LocalDate startDate = LocalDate.of(2025, 5, 1);
        LocalDate endDate = LocalDate.of(2025, 5, 28);
        timetable = new Timetable(startDate, endDate);

        // Create time slots for each physiotherapist
        createTimeSlots(sarah, startDate, endDate,
                new DayOfWeek[]{DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY, DayOfWeek.FRIDAY});
        createTimeSlots(michael, startDate, endDate,
                new DayOfWeek[]{DayOfWeek.TUESDAY, DayOfWeek.THURSDAY});
        createTimeSlots(emma, startDate, endDate,
                new DayOfWeek[]{DayOfWeek.MONDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY});
        createTimeSlots(james, startDate, endDate,
                new DayOfWeek[]{DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY});
    }

    private void createTimeSlots(Physiotherapist physio, LocalDate startDate, LocalDate endDate, DayOfWeek[] workDays) {
        LocalTime[] startTimes = {
            LocalTime.of(9, 0),
            LocalTime.of(10, 0),
            LocalTime.of(11, 0),
            LocalTime.of(13, 0),
            LocalTime.of(14, 0),
            LocalTime.of(15, 0),
            LocalTime.of(16, 0)
        };

        List<DayOfWeek> workDaysList = Arrays.asList(workDays);

        LocalDate current = startDate;
        while (!current.isAfter(endDate)) {
            if (workDaysList.contains(current.getDayOfWeek())) {
                for (LocalTime startTime : startTimes) {
                    LocalDateTime slotDateTime = LocalDateTime.of(current, startTime);
                    TimeSlot slot = new TimeSlot(slotDateTime, Duration.ofHours(1));
                    timetable.addAvailableSlot(physio, slot);
                }
            }
            current = current.plusDays(1);
        }
    }

    // Patient management
    public void addPatient(String name, String address, String telephone) {
        Patient patient = new Patient(nextPersonId++, name, address, telephone);
        patients.add(patient);
        System.out.println("Patient added: " + patient);
    }

    public void removePatient(int patientId) {
        Patient patientToRemove = null;
        for (Patient patient : patients) {
            if (patient.getId() == patientId) {
                patientToRemove = patient;
                break;
            }
        }

        if (patientToRemove != null) {
            // Cancel any future appointments
            List<Appointment> futureAppointments = patientToRemove.getFutureAppointments();
            for (Appointment appointment : futureAppointments) {
                appointment.cancel();
            }

            patients.remove(patientToRemove);
            System.out.println("Patient removed: " + patientToRemove);
        } else {
            System.out.println("Patient not found with ID: " + patientId);
        }
    }

    // Lookup and booking by expertise area
    public List<AvailableAppointment> findAppointmentsByExpertise(String expertise) {
        List<AvailableAppointment> availableAppointments = new ArrayList<>();

        for (Physiotherapist physio : physiotherapists) {
            if (physio.hasExpertise(expertise)) {
                List<Treatment> treatments = physio.getTreatmentsByExpertise(expertise);

                for (Treatment treatment : treatments) {
                    List<TimeSlot> availableSlots = timetable.getAvailableSlots(physio, treatment);

                    for (TimeSlot slot : availableSlots) {
                        availableAppointments.add(new AvailableAppointment(physio, treatment, slot));
                    }
                }
            }
        }

        return availableAppointments;
    }

    // Lookup and booking by physiotherapist
    public List<AvailableAppointment> findAppointmentsByPhysiotherapist(String physiotherapistName) {
        List<AvailableAppointment> availableAppointments = new ArrayList<>();

        for (Physiotherapist physio : physiotherapists) {
            if (physio.getFullName().toLowerCase().contains(physiotherapistName.toLowerCase())) {
                List<Treatment> treatments = physio.getAllTreatments();

                for (Treatment treatment : treatments) {
                    List<TimeSlot> availableSlots = timetable.getAvailableSlots(physio, treatment);

                    for (TimeSlot slot : availableSlots) {
                        availableAppointments.add(new AvailableAppointment(physio, treatment, slot));
                    }
                }
            }
        }

        return availableAppointments;
    }

    // Book an appointment
    public Appointment bookAppointment(int patientId, AvailableAppointment available) {
        Patient patient = null;
        for (Patient p : patients) {
            if (p.getId() == patientId) {
                patient = p;
                break;
            }
        }

        if (patient == null) {
            System.out.println("Patient not found with ID: " + patientId);
            return null;
        }

        try {
            Appointment appointment = new Appointment(
                    nextAppointmentId++,
                    patient,
                    available.getPhysiotherapist(),
                    available.getTreatment(),
                    available.getTimeSlot()
            );

            timetable.addAppointment(appointment);
            patient.addAppointment(appointment);
            System.out.println("Appointment booked: " + appointment);
            return appointment;
        } catch (IllegalArgumentException e) {
            System.out.println("Failed to book appointment: " + e.getMessage());
            return null;
        }
    }

    // Cancel an appointment
    public void cancelAppointment(int patientId, int appointmentId) {
        Patient patient = null;
        for (Patient p : patients) {
            if (p.getId() == patientId) {
                patient = p;
                break;
            }
        }

        if (patient == null) {
            System.out.println("Patient not found with ID: " + patientId);
            return;
        }

        for (Appointment appointment : patient.getAppointments()) {
            if (appointment.getId() == appointmentId && !appointment.isCancelled()) {
                patient.cancelAppointment(appointment);
                System.out.println("Appointment cancelled: " + appointment);
                return;
            }
        }

        System.out.println("Appointment not found with ID: " + appointmentId + " for patient ID: " + patientId);
    }

    // Check in for an appointment
    public void checkInAppointment(int appointmentId) {
        for (Appointment appointment : timetable.getAllAppointments()) {
            if (appointment.getId() == appointmentId && !appointment.isCancelled() && !appointment.isAttended()) {
                appointment.checkIn();
                System.out.println("Patient checked in for appointment: " + appointment);
                return;
            }
        }

        System.out.println("Valid appointment not found with ID: " + appointmentId);
    }

    // Generate end-of-term report
    public void generateReport() {
        List<Appointment> termAppointments = timetable.getAppointmentsInDateRange(
                timetable.getStartDate(), timetable.getEndDate());

        System.out.println("\n===== BOOST PHYSIO CLINIC REPORT =====");
        System.out.println("Term: " + timetable.getStartDate() + " to " + timetable.getEndDate());
        System.out.println("\n--- APPOINTMENTS BY PHYSIOTHERAPIST ---");

        // Group appointments by physiotherapist
        Map<Physiotherapist, List<Appointment>> physioAppointments = new HashMap<>();
        for (Appointment apt : termAppointments) {
            Physiotherapist physio = apt.getPhysiotherapist();
            if (!physioAppointments.containsKey(physio)) {
                physioAppointments.put(physio, new ArrayList<>());
            }
            physioAppointments.get(physio).add(apt);
        }

        // Print detailed report for each physiotherapist
        for (Map.Entry<Physiotherapist, List<Appointment>> entry : physioAppointments.entrySet()) {
            Physiotherapist physio = entry.getKey();
            List<Appointment> appointments = entry.getValue();

            System.out.println("\nPhysiotherapist: " + physio.getFullName());
            System.out.println("--------------------------------------------------");
            System.out.printf("%-20s %-20s %-30s %-10s\n",
                    "Treatment", "Patient", "Time", "Status");
            System.out.println("--------------------------------------------------");

            for (Appointment apt : appointments) {
                System.out.printf("%-20s %-20s %-30s %-10s\n",
                        apt.getTreatment().getName(),
                        apt.getPatient().getFullName(),
                        apt.getTimeSlot().getFormattedTimeRange(),
                        apt.getStatus());
            }
        }

        // Generate ranked list of physiotherapists by attended appointments
        System.out.println("\n--- PHYSIOTHERAPISTS RANKED BY ATTENDED APPOINTMENTS ---");
        System.out.println("--------------------------------------------------");
        System.out.printf("%-30s %-20s\n", "Physiotherapist", "Attended Appointments");
        System.out.println("--------------------------------------------------");

        List<Map.Entry<Physiotherapist, Integer>> rankedPhysios = new ArrayList<>();

        for (Physiotherapist physio : physiotherapists) {
            int attendedCount = 0;
            if (physioAppointments.containsKey(physio)) {
                for (Appointment apt : physioAppointments.get(physio)) {
                    if (apt.isAttended()) {
                        attendedCount++;
                    }
                }
            }
            rankedPhysios.add(Map.entry(physio, attendedCount));
        }

        // Sort by attended appointments in descending order
        Collections.sort(rankedPhysios, (a, b) -> b.getValue() - a.getValue());

        for (Map.Entry<Physiotherapist, Integer> entry : rankedPhysios) {
            System.out.printf("%-30s %-20d\n",
                    entry.getKey().getFullName(),
                    entry.getValue());
        }

        System.out.println("\n===== END OF REPORT =====");
    }

    // Helper methods
    public List<String> getAllExpertiseAreas() {
        List<String> areas = new ArrayList<>();
        for (Physiotherapist physio : physiotherapists) {
            for (String expertise : physio.getExpertiseAreas()) {
                if (!areas.contains(expertise)) {
                    areas.add(expertise);
                }
            }
        }
        return areas;
    }

    public List<Physiotherapist> getPhysiotherapists() {
        return new ArrayList<>(physiotherapists);
    }

    public List<Patient> getPatients() {
        return new ArrayList<>(patients);
    }

    public Patient getPatientById(int id) {
        for (Patient patient : patients) {
            if (patient.getId() == id) {
                return patient;
            }
        }
        return null;
    }
}
