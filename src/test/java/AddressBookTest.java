import addressbook.models.AddressBook;
import addressbook.models.BuddyInfo;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class AddressBookTest {

    private AddressBook addressBook;
    @Before
    public void setUp() {
        addressBook = new AddressBook("add1");
    }

    @Test
    public void testName() {
        assertEquals("address book name is not equal", "add1", addressBook.getName());
    }

    @Test
    public void testBuddies() {
        List<BuddyInfo> buddies = new ArrayList<BuddyInfo>();
        buddies.add(new BuddyInfo("Tosin"));
        addressBook.setBuddies(buddies);

        assertEquals("address book buddies are not equal", buddies, addressBook.getBuddies());
    }

    @Test
    public void testAddBuddy() {
        BuddyInfo b = new BuddyInfo("Tosin");
        b.setAddress("121 main st");
        b.setPhoneNumber("212-272");
        addressBook.addBuddy(b);

        assertTrue("Buddy was not added to buddies", addressBook.getBuddies().contains(b));
    }

    @Test
    public void testRemoveBuddy() {
        BuddyInfo b = new BuddyInfo("Tosin");
        b.setAddress("121 main st");
        b.setPhoneNumber("212-272");
        addressBook.addBuddy(b);

        assertTrue("address book has 1 buddy", addressBook.getBuddies().contains(b));

        BuddyInfo newBuddy = new BuddyInfo("Tosin");
        newBuddy.setAddress("121 main st");
        newBuddy.setPhoneNumber("212-272");

        addressBook.removeBuddy(newBuddy);
        assertFalse("Buddy was not removed from buddies", addressBook.getBuddies().contains(b));
    }

    @Test
    public void testEquals() {
        AddressBook otherAddressBook = new AddressBook("add2");

        assertFalse("address book are not the same", addressBook.equals(otherAddressBook));
    }

    @Test
    public void testToString() {
        BuddyInfo buddyInfo = new BuddyInfo("Tosin");
        buddyInfo.setId(1);
        buddyInfo.setName("Tosin");
        buddyInfo.setPhoneNumber("289-756-6354");
        buddyInfo.setAddress("121 Main St west");

        addressBook.addBuddy(buddyInfo);

        String expectedString = "Address book name is add1\n";
        expectedString += "Id\tName\tPhone Number\tAddress\n";
        expectedString += "1\tTosin\t289-756-6354\t121 Main St west\n";

        assertEquals("Buddyinfo tostring are not equal", expectedString, addressBook.toString());
    }
}