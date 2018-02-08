import addressbook.models.AddressBook;
import addressbook.models.BuddyInfo;
import org.junit.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class EntityManagerTest {

    private static EntityManagerFactory entityManagerFactory;
    private static EntityManager entityManager;

    @Before
    public void setup() {
        // Connecting to the database through EntityManagerFactory
        // connection details loaded from persistence.xml
        entityManagerFactory = Persistence.createEntityManagerFactory("address-book");
        entityManager = entityManagerFactory.createEntityManager();
    }

    @After
    public void tearDown() {
        // Closing connection
        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    public void testBuddyInfoPersistence() {

        List<BuddyInfo> buddyInfoList = getDefaultBuddies();

        EntityTransaction transaction = entityManager.getTransaction();

        // Creating a new transaction
        transaction.begin();

        for (BuddyInfo b : buddyInfoList)
            entityManager.persist(b);

        transaction.commit();

        // Querying the contents of the database using JPQL query
        Query query = entityManager.createQuery("SELECT b FROM BuddyInfo b");

        @SuppressWarnings("unchecked")
        List<BuddyInfo> listOfBuddyInfos = query.getResultList();
        assertReturnedBuddyInfoList(buddyInfoList, listOfBuddyInfos);
    }

    @Test
    public void testAddressBookPersistence() {
        List<BuddyInfo> buddyInfoList = getDefaultBuddies();

        EntityTransaction transaction = entityManager.getTransaction();

        // Creating a new transaction
        transaction.begin();

        String addressBookName = "Tosin's AddressBook";
        AddressBook addressBook = new AddressBook(addressBookName);
        addressBook.setBuddies(buddyInfoList);

        entityManager.persist(addressBook);

        transaction.commit();

        // Querying the contents of the database using JPQL query
        Query query = entityManager.createQuery("SELECT a FROM AddressBook a");

        @SuppressWarnings("unchecked")
        List<AddressBook> listOfAddressBook = query.getResultList();

        assertEquals("address book list size is not 1", 1, listOfAddressBook.size());
        for(AddressBook addressBookFromDb : listOfAddressBook) {
            assertTrue("valid id is not set", addressBookFromDb.getId() > 0);
            assertEquals("address book name is not equal", addressBookName, addressBookFromDb.getName());
            assertReturnedBuddyInfoList(buddyInfoList, addressBookFromDb.getBuddies());
        }
    }

    private void assertReturnedBuddyInfoList(List<BuddyInfo> expectedBuddies, List<BuddyInfo> actualBuddies) {
        assertEquals("buddy info list size is not 4", expectedBuddies.size(), actualBuddies.size());

        for (BuddyInfo b : actualBuddies) {
            assertTrue("valid id is not set", b.getId() > 0);
            assertTrue("invalid buddy info " + b.toString() + " returned", expectedBuddies.contains(b));
        }
    }

    private List<BuddyInfo> getDefaultBuddies() {
        List<BuddyInfo> buddies = new ArrayList<BuddyInfo>();

        buddies.add(new BuddyInfo("Tosin", "287-762-5631", "121 main st w"));
        buddies.add(new BuddyInfo("John", "287-762-5632", "122 main st w"));
        buddies.add(new BuddyInfo("Doe", "287-762-5633", "123 main st w"));
        buddies.add(new BuddyInfo("Jane", "287-762-5634", "124 main st w"));

        return buddies;
    }
}
