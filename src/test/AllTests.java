package test;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class AllTests {

    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(
                TimetableTest.class,
                TimeSlotTest.class,
                PhysiotherapistTest.class,
                PatientTest.class,
                ClinicSystemTest.class
        );

        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }

        if (result.wasSuccessful()) {
            System.out.println("All tests passed successfully!");
        } else {
            System.out.println("Some tests failed.");
        }
    }
}
