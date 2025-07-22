package project.ping.converter;

import project.ping.domain.Likes;
import project.ping.domain.Member;
import project.ping.domain.Post;
import project.ping.dto.LikesResponseDTO;

public class LikesConverter {

    public static Likes toLikes(Member member, Post post){
        return Likes.builder()
                .member(member)
                .post(post)
                .build();
    }

    public static LikesResponseDTO.countLikesDTO toCountLikes(Long count){
        return LikesResponseDTO.countLikesDTO.builder()
                .count(count)
                .build();
    }
}
