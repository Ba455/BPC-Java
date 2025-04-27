package test;

import clinic.*;

import static org.junit.Assert.*;
import org.junit.Test;
import java.time.Duration;
import java.time.LocalDateTime;

public class TimeSlotTest {  // Changed to public

    public TimeSlotTest() {
    }  // Added public constructor

    @Test
    public void testTimeSlotOverlaps() {
        LocalDateTime now = LocalDateTime.now();
        TimeSlot slot1 = new TimeSlot(now, Duration.ofHours(1));
        TimeSlot slot2 = new TimeSlot(now.plusMinutes(30), Duration.ofHours(1));
        assertTrue(slot1.overlaps(slot2));

        TimeSlot slot3 = new TimeSlot(now.plusHours(2), Duration.ofMinutes(30));
        assertFalse(slot1.overlaps(slot3));
    }

    @Test
    public void testIsFuture() {
        TimeSlot past = new TimeSlot(LocalDateTime.now().minusDays(1), Duration.ofHours(1));
        TimeSlot future = new TimeSlot(LocalDateTime.now().plusDays(1), Duration.ofHours(1));
        assertFalse(past.isFuture());
        assertTrue(future.isFuture());
    }
}
