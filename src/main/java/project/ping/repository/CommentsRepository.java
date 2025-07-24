package project.ping.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.ping.domain.Comments;

public interface CommentsRepository extends JpaRepository<Comments, Long> {
}
