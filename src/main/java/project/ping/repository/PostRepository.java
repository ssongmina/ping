package project.ping.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.ping.domain.Post;

public interface PostRepository extends JpaRepository<Post, Long> {

}
