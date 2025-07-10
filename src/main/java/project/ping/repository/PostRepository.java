package project.ping.repository;

import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import project.ping.domain.Member;
import project.ping.domain.Post;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("""
    SELECT p FROM Post p
    WHERE p.member IN (
        SELECT f.following FROM Follow f WHERE f.follower = :member
    )
    AND p.createdAt = (
        SELECT MAX(p2.createdAt) FROM Post p2 WHERE p2.member = p.member
    )
   ORDER BY p.createdAt DESC
    """)
    List<Post> findLatestPostsOfFollowings(@Param("member") Member member);

}
