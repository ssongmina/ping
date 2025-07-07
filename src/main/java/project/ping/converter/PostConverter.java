package project.ping.converter;

import project.ping.domain.Member;
import project.ping.domain.Post;
import project.ping.dto.PostRequestDTO;

public class PostConverter {

    public static Post toPost(Member member, PostRequestDTO.postDTO request){
        return Post.builder()
                .member(member)
                .content(request.getContent())
                .build();
    }

}
