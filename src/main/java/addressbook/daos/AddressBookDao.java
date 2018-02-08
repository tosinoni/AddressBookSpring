package addressbook.daos;

import addressbook.models.AddressBook;
import org.springframework.data.repository.CrudRepository;

public interface AddressBookDao extends CrudRepository<AddressBook, Long> {
    AddressBook findByName(String name);
}
