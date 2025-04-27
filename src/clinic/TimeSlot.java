package clinic;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimeSlot {

    private LocalDateTime startTime;
    private Duration duration;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE d MMMM yyyy, HH:mm");

    public TimeSlot(LocalDateTime startTime, Duration duration) {
        this.startTime = startTime;
        this.duration = duration;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return startTime.plus(duration);
    }

    public Duration getDuration() {
        return duration;
    }

    public boolean overlaps(TimeSlot other) {
        return !getStartTime().isAfter(other.getEndTime())
                && !getEndTime().isBefore(other.getStartTime());
    }

    public boolean isFuture() {
        return startTime.isAfter(LocalDateTime.now());
    }

    public String getFormattedStartTime() {
        return startTime.format(formatter);
    }

    public String getFormattedTimeRange() {
        return startTime.format(formatter) + "-"
                + startTime.plus(duration).format(DateTimeFormatter.ofPattern("HH:mm"));
    }

    @Override
    public String toString() {
        return getFormattedTimeRange();
    }
}
