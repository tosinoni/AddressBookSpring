package addressbook.resources;


import addressbook.Util;
import addressbook.daos.AddressBookDao;
import addressbook.daos.BuddyInfoDao;
import addressbook.models.AddressBook;
import addressbook.models.BuddyInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;


@RestController
@RequestMapping("/")
public class AddressBookResource {

    @Autowired
    AddressBookDao addressBookDao;

    @Autowired
    BuddyInfoDao buddyInfoDao;

    @GetMapping
    public ResponseEntity getAddressBook(@RequestParam(value="name", required = false) String name,
                                         @RequestParam(value="id", required = false) String id ) {

        Set<AddressBook> addressBookSet = new HashSet<>();

        if(!StringUtils.isEmpty(name)) {
            Util.addIfNotNull(addressBookSet, addressBookDao.findByName(name));
        }

        if(!StringUtils.isEmpty(id) && StringUtils.isNumeric(id)) {
            Util.addIfNotNull(addressBookSet, addressBookDao.findOne(Long.parseLong(id)));
        }

        if(addressBookSet.isEmpty()) {
            addressBookDao.findAll().forEach(addressBook -> addressBookSet.add(addressBook));
        }

        return new ResponseEntity<>(addressBookSet, HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity addressBook(@RequestBody AddressBook addressBook) {
        if (addressBook == null || StringUtils.isEmpty(addressBook.getName())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid request. Please provide address book name");
        } else if (addressBookDao.findByName(addressBook.getName()) != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid request. Address book " + addressBook.getName() +  " already exists.");
        }

        AddressBook savedAddressBook = addressBookDao.save(addressBook);
        return new ResponseEntity<>(savedAddressBook, HttpStatus.CREATED);
    }

    @PostMapping(value="/id/{id}", consumes = "application/json")
    public ResponseEntity addBuddies(@PathVariable("id") String id, @RequestBody Set<BuddyInfo> buddies) {

        if(StringUtils.isEmpty(id) && StringUtils.isNumeric(id)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid request. Please provide a valid id");
        }

        AddressBook addressBook = addressBookDao.findOne(Long.parseLong(id));

        if(addressBook == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid request. Address book with id " + id + " does not exist");
        }

        String validationError = validateBuddies(buddies);
        if (!StringUtils.isEmpty(validationError)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validationError);
        }

        addressBook.addBuddies(buddies);

        AddressBook savedAddressBook = addressBookDao.save(addressBook);
        return new ResponseEntity<>(savedAddressBook, HttpStatus.CREATED);
    }

    @DeleteMapping(value="/id/{id}")
    public ResponseEntity removeBuddies(@PathVariable("id") String id,
                                        @RequestParam(value="name", required = false) String name,
                                        @RequestParam(value="buddyId", required = false) String buddyId ) {

        if(StringUtils.isEmpty(id) && StringUtils.isNumeric(id)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid request. Please provide a valid address id");
        }

        AddressBook addressBook = addressBookDao.findOne(Long.parseLong(id));

        if(addressBook == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid request. Address book with id " + id + " does not exist");
        } else if(StringUtils.isEmpty(name) && (StringUtils.isEmpty(buddyId)|| !StringUtils.isNumeric(buddyId))) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please provide a valid buddy Id or name");
        }

        BuddyInfo buddyInfo = buddyInfoDao.findByName(name);
        if (!StringUtils.isEmpty(buddyId)) {
            buddyInfo = buddyInfoDao.findOne(Long.parseLong(buddyId));

        }

        if(buddyInfo == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid request. Buddy info does not exist");
        }

        addressBook.removeBuddy(buddyInfo);

        AddressBook savedAddressBook = addressBookDao.save(addressBook);
        return new ResponseEntity<>(savedAddressBook, HttpStatus.OK);
    }

    private String validateBuddies(Set<BuddyInfo> buddies) {
        if (Util.isCollectionEmpty(buddies)) {
            return "Invalid request. Please provide at least one buddy";
        }

        for (BuddyInfo buddy : buddies) {
            if (StringUtils.isEmpty(buddy.getName())) {
                return "Invalid request. Buddy with no name provided";
            } else if(buddyInfoDao.findByName(buddy.getName()) != null) {
                return "Invalid request. Buddy with name " + buddy.getName() + " already exists";
            }
        }

        return "";
    }
}
