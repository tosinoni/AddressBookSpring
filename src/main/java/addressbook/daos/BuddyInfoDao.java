package addressbook.daos;

import addressbook.models.BuddyInfo;
import org.springframework.data.repository.CrudRepository;

public interface BuddyInfoDao extends CrudRepository<BuddyInfo, Long> {
    BuddyInfo findByName(String name);
}
