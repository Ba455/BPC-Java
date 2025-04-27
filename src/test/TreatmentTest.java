package test;

import clinic.*;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class TreatmentTest {

    public TreatmentTest() {
    }

    @Test
    void testTreatmentEquality() {
        Treatment t1 = new Treatment(1, "Massage", "Physio");
        Treatment t2 = new Treatment(1, "Acupuncture", "Physio");
        assertEquals(t1, t2);
        assertEquals(t1.hashCode(), t2.hashCode());
    }
}
