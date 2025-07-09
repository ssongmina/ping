package project.ping.converter;

import project.ping.domain.Follow;
import project.ping.domain.Member;

public class FollowConverter {

    public static Follow toFollow(Member follower, Member following){
        return Follow.builder()
                .follower(follower)
                .following(following)
                .build();
    }
}
