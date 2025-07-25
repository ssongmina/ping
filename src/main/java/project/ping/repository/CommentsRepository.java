package project.ping.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.ping.domain.Comments;
import project.ping.domain.Post;

import java.util.List;

public interface CommentsRepository extends JpaRepository<Comments, Long> {
    List<Comments> findByPost(Post post);
}
