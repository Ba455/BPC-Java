package clinic;

public class AvailableAppointment {

    private Physiotherapist physiotherapist;
    private Treatment treatment;
    private TimeSlot timeSlot;

    public AvailableAppointment(Physiotherapist physiotherapist, Treatment treatment, TimeSlot timeSlot) {
        this.physiotherapist = physiotherapist;
        this.treatment = treatment;
        this.timeSlot = timeSlot;
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

    @Override
    public String toString() {
        return "Treatment: " + treatment.getName()
                + " with " + physiotherapist.getFullName()
                + " at " + timeSlot.getFormattedStartTime();
    }
}
