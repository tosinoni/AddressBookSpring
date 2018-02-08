package resources;

import addressbook.AddressBookApplication;
import addressbook.models.AddressBook;
import addressbook.models.BuddyInfo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {AddressBookApplication.class})
public class AddressBookResourceTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private static String url;

    @Before
    public void setUp() {
        url = "http://localhost:" + port + "/address/";
    }
    @Test
    public void testGetAddressBook() throws Exception {
        ResponseEntity<AddressBook[]> responseEntity = this.restTemplate.getForEntity(url, AddressBook[].class);
        assertEquals("Response code is not 200", HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void testCreateAddressBook() throws Exception {
        AddressBook addressBook = getDefaultAddressBook();
        ResponseEntity<AddressBook> responseEntity = this.restTemplate.postForEntity(url, addressBook, AddressBook.class);

        AddressBook returnedAddressBook = responseEntity.getBody();
        assertEquals("Response code is not 201", HttpStatus.CREATED, responseEntity.getStatusCode());
        assertTrue("address book id is not valid", returnedAddressBook.getId() > 0);
        assertEquals("address book object is not equal", addressBook, returnedAddressBook);
    }

//    @Test
//    public void testAddBuddies() throws Exception {
//        AddressBook addressBook = getDefaultAddressBook();
//        ResponseEntity<AddressBook> responseEntity = this.restTemplate.postForEntity(url, addressBook, AddressBook.class);
//        AddressBook returnedAddressBook = responseEntity.getBody();
//
//        //testing the returned address book
//        assertTrue("address book id is not valid", returnedAddressBook.getId() > 0);
//
//
//        //add buddies to address book
//        Set<BuddyInfo> buddyInfoSet = getDefaultBuddies();
//
//        String addBuddiesUrl = url + "id/" + returnedAddressBook.getId();
//        responseEntity = this.restTemplate.postForEntity(addBuddiesUrl, buddyInfoSet, AddressBook.class);
//        AddressBook returnedAddressBookWithBuddies = responseEntity.getBody();
//
//        assertEquals("Response code is not 200", HttpStatus.OK, responseEntity.getStatusCode());
//        assertEquals("address book id does not match", returnedAddressBook.getId(), returnedAddressBookWithBuddies.getId());
//        assertEquals("buddy infos are  not equal", buddyInfoSet, returnedAddressBookWithBuddies.getBuddies());
//    }


    public AddressBook getDefaultAddressBook() {
        AddressBook addressBook = new AddressBook();
        addressBook.setName("Address 1");

        return addressBook;
    }

    public Set<BuddyInfo> getDefaultBuddies() {
        BuddyInfo buddyInfo1 = new BuddyInfo("Tosin", "287-762-5631", "121 main st w");
        BuddyInfo buddyInfo2 = new BuddyInfo("John", "287-762-5632", "122 main st w");
        BuddyInfo buddyInfo3 = new BuddyInfo("Doe", "287-762-5633", "123 main st w");
        BuddyInfo buddyInfo4 = new BuddyInfo("Jane", "287-762-5634", "124 main st w");

        Set<BuddyInfo> buddyInfoSet = new HashSet<>();
        buddyInfoSet.add(buddyInfo1);
        buddyInfoSet.add(buddyInfo2);
        buddyInfoSet.add(buddyInfo3);
        buddyInfoSet.add(buddyInfo4);

        return buddyInfoSet;
    }
}
