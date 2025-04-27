package test;

import clinic.*;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.time.LocalDateTime;
import java.util.List;

public class PatientTest {  // Changed to public

    public Patient patient;
    public Appointment appointment;

    public PatientTest() {
    }  // Added public constructor

    @Before
    public void setUp() {
        patient = new Patient(1, "John Doe", "Street", "12345");
        Physiotherapist physio = new Physiotherapist(2, "Dr. Smith", "Clinic", "67890");
        Treatment treatment = new Treatment(1, "Massage", "Physio");
        TimeSlot slot = new TimeSlot(LocalDateTime.now().plusDays(1), java.time.Duration.ofHours(1));
        appointment = new Appointment(1, patient, physio, treatment, slot);
    }

    @Test
    public void testAddAppointment() {
        patient.addAppointment(appointment);
        assertFalse(patient.getAppointments().isEmpty());
        assertEquals(1, patient.getAppointments().size());
    }

    @Test
    public void testFutureAppointments() {
        patient.addAppointment(appointment);
        List<Appointment> future = patient.getFutureAppointments();
        assertFalse(future.isEmpty());
        assertEquals(appointment, future.get(0));
    }

    @Test
    public void testCancelAppointment() {
        patient.addAppointment(appointment);
        patient.cancelAppointment(appointment);
        assertEquals(Appointment.Status.CANCELLED, appointment.getStatus());
    }
}
