package group.bkwebapp.repository;

import group.bkwebapp.domain.BuddyRead;
import group.bkwebapp.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByBuddyReadIdAndPageNumber(Long buddyReadId, Integer pageNumber);
}
