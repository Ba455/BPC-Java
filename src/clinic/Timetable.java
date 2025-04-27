package clinic;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Timetable {

    private LocalDate startDate;
    private LocalDate endDate;
    private Map<Physiotherapist, List<TimeSlot>> availableSlots;
    private List<Appointment> appointments;

    public Timetable(LocalDate startDate, LocalDate endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.availableSlots = new HashMap<>();
        this.appointments = new ArrayList<>();
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void addAvailableSlot(Physiotherapist physiotherapist, TimeSlot slot) {
        if (!isWithinTerm(slot.getStartTime())) {
            return;
        }

        if (!availableSlots.containsKey(physiotherapist)) {
            availableSlots.put(physiotherapist, new ArrayList<>());
        }
        availableSlots.get(physiotherapist).add(slot);
    }

    public boolean isWithinTerm(LocalDateTime time) {
        LocalDate date = time.toLocalDate();
        return !date.isBefore(startDate) && !date.isAfter(endDate);
    }

    public boolean isSlotAvailable(Physiotherapist physiotherapist, TimeSlot slot) {
        if (!isWithinTerm(slot.getStartTime())) {
            return false;
        }

        // Check if the slot exists in the physiotherapist's available slots
        boolean slotExists = false;
        if (availableSlots.containsKey(physiotherapist)) {
            for (TimeSlot availableSlot : availableSlots.get(physiotherapist)) {
                if (availableSlot.getStartTime().equals(slot.getStartTime())) {
                    slotExists = true;
                    break;
                }
            }
        }

        if (!slotExists) {
            return false;
        }

        // Check if the slot is already booked
        for (Appointment appointment : appointments) {
            if (appointment.getPhysiotherapist().equals(physiotherapist)
                    && !appointment.isCancelled()
                    && appointment.getTimeSlot().overlaps(slot)) {
                return false;
            }
        }

        return true;
    }

    public List<TimeSlot> getAvailableSlots(Physiotherapist physiotherapist, Treatment treatment) {
        List<TimeSlot> available = new ArrayList<>();

        if (availableSlots.containsKey(physiotherapist)) {
            for (TimeSlot slot : availableSlots.get(physiotherapist)) {
                if (isSlotAvailable(physiotherapist, slot)) {
                    available.add(slot);
                }
            }
        }

        return available;
    }

    public void addAppointment(Appointment appointment) {
        if (isSlotAvailable(appointment.getPhysiotherapist(), appointment.getTimeSlot())) {
            appointments.add(appointment);
        } else {
            throw new IllegalArgumentException("The selected time slot is not available.");
        }
    }

    public List<Appointment> getAllAppointments() {
        return new ArrayList<>(appointments);
    }

    public List<Appointment> getAppointmentsInDateRange(LocalDate start, LocalDate end) {
        List<Appointment> result = new ArrayList<>();

        for (Appointment appointment : appointments) {
            LocalDate appointmentDate = appointment.getTimeSlot().getStartTime().toLocalDate();
            if (!appointmentDate.isBefore(start) && !appointmentDate.isAfter(end)) {
                result.add(appointment);
            }
        }

        return result;
    }
}
