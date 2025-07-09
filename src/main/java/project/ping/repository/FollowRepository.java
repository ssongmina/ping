package project.ping.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.ping.domain.Follow;
import project.ping.domain.Member;

import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    boolean existsByFollowerAndFollowing(Member follower, Member following);

    Optional<Follow> findByFollowerAndFollowing(Member follower, Member following);
}
