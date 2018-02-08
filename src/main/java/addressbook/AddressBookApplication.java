package addressbook;

import addressbook.daos.AddressBookDao;
import addressbook.models.AddressBook;
import addressbook.models.BuddyInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SpringBootApplication
public class AddressBookApplication {
    private static final Logger log = LoggerFactory.getLogger(AddressBookApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(AddressBookApplication.class);
    }

    @Bean
    public CommandLineRunner demo(final AddressBookDao repository) {
        return (args) -> {
            // save a couple of customers
            AddressBook addressBook = new AddressBook("addressBook1");
            Set< BuddyInfo > buddies = new HashSet<>();

            buddies.add(new BuddyInfo("Tosin", "287-762-5631", "121 main st w"));
            buddies.add(new BuddyInfo("John", "287-762-5632", "122 main st w"));
            addressBook.addBuddies(buddies);
            repository.save(addressBook);


            // fetch all customers
            log.info("AddressBook found with findAll():");
            log.info("-------------------------------");
            for (AddressBook customer : repository.findAll()) {
                log.info(customer.toString());
            }
            log.info("");

        };
    }

}
