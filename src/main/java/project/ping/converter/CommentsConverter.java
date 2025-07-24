package project.ping.converter;

import project.ping.domain.Comments;
import project.ping.domain.Member;
import project.ping.domain.Post;
import project.ping.dto.CommentsRequestDTO;
import project.ping.dto.CommentsResponseDTO;

import java.util.Date;

public class CommentsConverter {

    public static Comments toComments(Member member, Post post, Comments comments, CommentsRequestDTO.WriteCommentsDTO request){
        return Comments.builder()
                .member(member)
                .post(post)
                .parent(comments)
                .content(request.getContext())
                .build();
    }

    public static CommentsResponseDTO.CommentsDTO completeComments(Comments comments){
        return CommentsResponseDTO.CommentsDTO.builder()
                .commentsId(comments.getId())
                .createdAt(comments.getCreatedAt())
                .build();
    }


}
