import addressbook.models.BuddyInfo;
import org.junit.Test;

import static org.junit.Assert.*;

public class BuddyInfoTest {

    @Test
    public void testId() {
        BuddyInfo buddyInfo = new BuddyInfo("Tosin");
        buddyInfo.setId(1);

        assertEquals("Expected id to be the same", 1, buddyInfo.getId());
    }

    @Test
    public void testName() {
        BuddyInfo buddyInfo = new BuddyInfo("Tosin");

        assertEquals("Expected name to be the same", "Tosin", buddyInfo.getName());
    }

    @Test
    public void testPhoneNumber() {
        BuddyInfo buddyInfo = new BuddyInfo("Tosin");
        buddyInfo.setPhoneNumber("289-756-6354");

        assertEquals("Expected name to be the same", "289-756-6354", buddyInfo.getPhoneNumber());
    }

    @Test
    public void testAddress() {
        BuddyInfo buddyInfo = new BuddyInfo("Tosin");
        buddyInfo.setAddress("121 Main St west");

        assertEquals("Expected name to be the same", "121 Main St west", buddyInfo.getAddress());
    }

    @Test
    public void testEquals() {
        BuddyInfo buddyInfo = new BuddyInfo("Tosin");

        BuddyInfo otherBuddyInfo = new BuddyInfo("Moe");

        assertFalse("buddy info are the same", buddyInfo.equals(otherBuddyInfo));
    }

    @Test
    public void testToString() {
        BuddyInfo buddyInfo = new BuddyInfo("Tosin");
        buddyInfo.setId(1);
        buddyInfo.setPhoneNumber("289-756-6354");
        buddyInfo.setAddress("121 Main St west");

        String expectedString = "1\tTosin\t289-756-6354\t121 Main St west";
        assertEquals("Buddyinfo tostring are not equal", expectedString, buddyInfo.toString());
    }
}