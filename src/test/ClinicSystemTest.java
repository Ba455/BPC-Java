package test;

import clinic.*;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.time.LocalDateTime;
import java.util.List;

public class ClinicSystemTest {

    public ClinicSystem clinic;

    public ClinicSystemTest() {
    }

    @Before
    public void setUp() {
        clinic = new ClinicSystem();
        clinic.initialize();
    }

    @Test
    public void testInitialize() {
        assertFalse(clinic.getPhysiotherapists().isEmpty());
        assertFalse(clinic.getPatients().isEmpty());
        assertFalse(clinic.getAllExpertiseAreas().isEmpty());
    }

    @Test
    public void testAddRemovePatient() {
        int initialCount = clinic.getPatients().size();
        clinic.addPatient("New Patient", "Address", "12345");
        assertEquals(initialCount + 1, clinic.getPatients().size());

        Patient p = clinic.getPatients().get(initialCount);
        clinic.removePatient(p.getId());
        assertEquals(initialCount, clinic.getPatients().size());
    }

    @Test
    public void testAppointmentBooking() {
        List<AvailableAppointment> apps = clinic.findAppointmentsByExpertise("Physiotherapy");
        assertFalse(apps.isEmpty());

        Patient patient = clinic.getPatients().get(0);
        AvailableAppointment app = apps.get(0);
        Appointment booked = clinic.bookAppointment(patient.getId(), app);
        assertNotNull(booked);
        assertTrue(patient.getAppointments().contains(booked));
    }

    @Test
    public void testCancelAppointment() {
        Patient patient = clinic.getPatients().get(0);
        AvailableAppointment app = clinic.findAppointmentsByExpertise("Physiotherapy").get(0);
        Appointment booked = clinic.bookAppointment(patient.getId(), app);

        clinic.cancelAppointment(patient.getId(), booked.getId());
        assertEquals(Appointment.Status.CANCELLED, booked.getStatus());
    }
}
