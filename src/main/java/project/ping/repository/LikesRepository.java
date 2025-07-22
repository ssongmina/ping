package project.ping.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.ping.domain.Likes;
import project.ping.domain.Member;
import project.ping.domain.Post;

public interface LikesRepository extends JpaRepository<Likes, Long> {

    Likes findByMemberAndPost(Member member, Post post);
}
