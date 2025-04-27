package test;

import clinic.*;

import static org.junit.Assert.*;
import org.junit.Test;

public class PhysiotherapistTest {  // Changed to public

    public PhysiotherapistTest() {
    }  // Added public constructor

    @Test
    public void testExpertiseManagement() {
        Physiotherapist physio = new Physiotherapist(1, "Dr. Smith", "Clinic", "12345");
        physio.addExpertise("Physiotherapy");
        assertTrue(physio.getExpertiseAreas().contains("Physiotherapy"));

        physio.removeExpertise("Physiotherapy");
        assertFalse(physio.getExpertiseAreas().contains("Physiotherapy"));
    }

    @Test
    public void testTreatmentAssociation() {
        Physiotherapist physio = new Physiotherapist(1, "Dr. Smith", "Clinic", "12345");
        physio.addExpertise("Physiotherapy");
        Treatment treatment = new Treatment(1, "Massage", "Physiotherapy");
        physio.addTreatment(treatment);

        assertFalse(physio.getTreatmentsByExpertise("Physiotherapy").isEmpty());
        assertEquals(treatment, physio.getTreatmentsByExpertise("Physiotherapy").get(0));
    }
}
