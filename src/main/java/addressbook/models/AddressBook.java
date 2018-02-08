package addressbook.models;

import addressbook.Util;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
public class AddressBook {

    @Id
    @GeneratedValue
    private long id;

    private String name;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<BuddyInfo> buddies;

    public AddressBook() {
        this(null);
    }
    public AddressBook(String name) {
        this.name = name;
        this.buddies = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<BuddyInfo> getBuddies() {
        return buddies;
    }

    public void setBuddies(List<BuddyInfo> buddies) {
        this.buddies = buddies;
    }

    public void addBuddy(BuddyInfo buddyInfo) {
        if (buddyInfo != null)
            buddies.add(buddyInfo);
    }

    public void addBuddies(Set<BuddyInfo> buddyInfos) {
        if (!Util.isCollectionEmpty(buddyInfos))
            buddies.addAll(buddyInfos);
    }

    public void removeBuddy(BuddyInfo buddyInfo) {
        buddies.remove(buddyInfo);
    }

    public String toString() {
        String addressInfo = "Address book name is " + name + "\n";
        addressInfo += "Id\tName\tPhone Number\tAddress\n";
        for (BuddyInfo buddyInfo : buddies) {
            addressInfo += buddyInfo.toString() + "\n";
        }

        return addressInfo;
    }

    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        else if (!(obj instanceof  AddressBook))
            return false;

        AddressBook a = (AddressBook) obj;

        return Objects.equals(name, a.name) && Objects.equals(buddies, a.buddies);
    }

    public void print() {
        System.out.println(toString());
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public static void main(String[] args) {
        AddressBook addressBook = new AddressBook("Tosin's AddressBook");

        BuddyInfo buddyInfo1 = new BuddyInfo("Tosin", "287-762-5631", "121 main st w");
        BuddyInfo buddyInfo2 = new BuddyInfo("John", "287-762-5632", "122 main st w");
        BuddyInfo buddyInfo3 = new BuddyInfo("Doe", "287-762-5633", "123 main st w");
        BuddyInfo buddyInfo4 = new BuddyInfo("Jane", "287-762-5634", "124 main st w");

        addressBook.addBuddy(buddyInfo1);
        addressBook.addBuddy(buddyInfo2);
        addressBook.addBuddy(buddyInfo3);
        addressBook.addBuddy(buddyInfo4);

        addressBook.print();
    }
}
