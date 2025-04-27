package clinic;

import java.util.ArrayList;
import java.util.List;

public class Patient extends Person {

    private List<Appointment> appointments;

    public Patient(int id, String fullName, String address, String telephone) {
        super(id, fullName, address, telephone);
        this.appointments = new ArrayList<>();
    }

    public void addAppointment(Appointment appointment) {
        appointments.add(appointment);
    }

    public List<Appointment> getAppointments() {
        return new ArrayList<>(appointments);
    }

    public List<Appointment> getFutureAppointments() {
        List<Appointment> futureAppointments = new ArrayList<>();
        for (Appointment appointment : appointments) {
            if (appointment.isFuture() && !appointment.isCancelled()) {
                futureAppointments.add(appointment);
            }
        }
        return futureAppointments;
    }

    public void cancelAppointment(Appointment appointment) {
        if (appointments.contains(appointment)) {
            appointment.cancel();
        }
    }

    public void removeAppointment(Appointment appointment) {
        appointments.remove(appointment);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
