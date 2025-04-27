package clinic;

public class Appointment {

    public enum Status {
        BOOKED, CANCELLED, ATTENDED
    }

    private int id;
    private Patient patient;
    private Physiotherapist physiotherapist;
    private Treatment treatment;
    private TimeSlot timeSlot;
    private Status status;

    public Appointment(int id, Patient patient, Physiotherapist physiotherapist,
            Treatment treatment, TimeSlot timeSlot) {
        this.id = id;
        this.patient = patient;
        this.physiotherapist = physiotherapist;
        this.treatment = treatment;
        this.timeSlot = timeSlot;
        this.status = Status.BOOKED;
    }

    public int getId() {
        return id;
    }

    public Patient getPatient() {
        return patient;
    }

    public Physiotherapist getPhysiotherapist() {
        return physiotherapist;
    }

    public Treatment getTreatment() {
        return treatment;
    }

    public TimeSlot getTimeSlot() {
        return timeSlot;
    }

    public Status getStatus() {
        return status;
    }

    public boolean isCancelled() {
        return status == Status.CANCELLED;
    }

    public boolean isAttended() {
        return status == Status.ATTENDED;
    }

    public boolean isFuture() {
        return timeSlot.isFuture();
    }

    public void cancel() {
        this.status = Status.CANCELLED;
    }

    public void checkIn() {
        this.status = Status.ATTENDED;
    }

    @Override
    public String toString() {
        return "Appointment [ID: " + id + ", Patient: " + patient.getFullName()
                + ", Physiotherapist: " + physiotherapist.getFullName()
                + ", Treatment: " + treatment.getName()
                + ", Time: " + timeSlot.getFormattedTimeRange()
                + ", Status: " + status + "]";
    }
}
