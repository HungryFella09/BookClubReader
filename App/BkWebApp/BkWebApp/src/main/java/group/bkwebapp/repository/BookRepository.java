package group.bkwebapp.repository;

import group.bkwebapp.domain.BuddyRead;
import group.bkwebapp.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface BookRepository extends JpaRepository<BuddyRead, Long> {

    Optional<BuddyRead> findBuddyReadByKey(String key);

}
