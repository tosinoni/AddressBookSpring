package addressbook.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class BuddyInfo {

    @Id
    @GeneratedValue
    private long id;

    private String name;
    private String address;
    private String phoneNumber;

    public BuddyInfo() {

    }
    public BuddyInfo(String name) {
        this.name = name;
    }

    public BuddyInfo(String name, String address, String phoneNumber) {
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String toString() {
        return id + "\t" + name + "\t" + phoneNumber + "\t" + address;
    }

    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        else if (!(obj instanceof  BuddyInfo))
            return false;

        BuddyInfo b = (BuddyInfo) obj;

        return b.name.equals(name) && Objects.equals(address, b.address) && Objects.equals(phoneNumber, b.phoneNumber);
    }
}
