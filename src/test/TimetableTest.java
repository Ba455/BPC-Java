package test;

import clinic.*;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

public class TimetableTest {

    public Timetable timetable;
    public Physiotherapist physio;
    public TimeSlot slot;

    public TimetableTest() {
    }

    @Before
    public void setUp() {
        LocalDate start = LocalDate.of(2025, Month.MAY, 1);
        LocalDate end = LocalDate.of(2025, Month.MAY, 28);
        timetable = new Timetable(start, end);

        physio = new Physiotherapist(1, "Dr. Smith", "Clinic", "12345");
        slot = new TimeSlot(LocalDateTime.of(2025, 5, 15, 10, 0),
                java.time.Duration.ofHours(1));
        timetable.addAvailableSlot(physio, slot);
    }

    @Test
    public void testSlotAvailability() {
        assertTrue(timetable.isSlotAvailable(physio, slot));

        Treatment treatment = new Treatment(1, "Massage", "Physio");
        Appointment appointment = new Appointment(1, new Patient(2, "John", "Street", "555"),
                physio, treatment, slot);
        timetable.addAppointment(appointment);

        assertFalse(timetable.isSlotAvailable(physio, slot));
    }

    @Test
    public void testDateRangeAppointments() {
        Treatment treatment = new Treatment(1, "Massage", "Physio");
        Appointment appointment = new Appointment(1, new Patient(2, "John", "Street", "555"),
                physio, treatment, slot);
        timetable.addAppointment(appointment);

        LocalDate start = LocalDate.of(2025, 5, 1);
        LocalDate end = LocalDate.of(2025, 5, 31);
        List<Appointment> apps = timetable.getAppointmentsInDateRange(start, end);
        assertEquals(1, apps.size());
    }
}
