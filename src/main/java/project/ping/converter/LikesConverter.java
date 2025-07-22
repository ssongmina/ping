package project.ping.converter;

import project.ping.domain.Likes;
import project.ping.domain.Member;
import project.ping.domain.Post;

public class LikesConverter {

    public static Likes toLikes(Member member, Post post){
        return Likes.builder()
                .member(member)
                .post(post)
                .build();

    }
}
