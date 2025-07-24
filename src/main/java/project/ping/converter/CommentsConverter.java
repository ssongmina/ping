package project.ping.converter;

import project.ping.domain.Comments;
import project.ping.domain.Member;
import project.ping.domain.Post;
import project.ping.dto.CommentsRequestDTO;

public class CommentsConverter {

    public static Comments toComments(Member member, Post post, Comments comments, CommentsRequestDTO.WriteCommentsDTO request){
        return Comments.builder()
                .member(member)
                .post(post)
                .parent(comments)
                .content(request.getContext())
                .build();
    }


}
