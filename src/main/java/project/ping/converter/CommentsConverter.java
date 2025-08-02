package project.ping.converter;

import project.ping.domain.Comments;
import project.ping.domain.Member;
import project.ping.domain.Post;
import project.ping.dto.CommentsRequestDTO;
import project.ping.dto.CommentsResponseDTO;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

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
                .content(comments.getContent())
                .nickname(comments.getMember().getNickname())
                .createdAt(comments.getCreatedAt())
                .build();
    }

    public static CommentsResponseDTO.CommentsListDTO toCommentsDTO(Comments comments){
        return CommentsResponseDTO.CommentsListDTO.builder()
                .commentsId(comments.getId())
                .content(comments.getContent())
                .nickname(comments.getMember().getNickname())
                .createdAt(comments.getCreatedAt())
                .replies(new ArrayList<>())
                .build();
    }

    public static List<CommentsResponseDTO.CommentsListDTO> toCommentsList(List<Comments> commentsList) {

        Map<Long, CommentsResponseDTO.CommentsListDTO> map = new HashMap<>();
        List<CommentsResponseDTO.CommentsListDTO> topLevelComments = new ArrayList<>();

        // 모든 댓글을 DTO로 변환하고 map에 저장
        for (Comments comment : commentsList) {
            map.put(comment.getId(), toCommentsDTO(comment));
        }

        // 부모-자식 연결
        for (Comments comment : commentsList) {
            Comments parent = comment.getParent();
            if (parent != null) {
                CommentsResponseDTO.CommentsListDTO parentDTO = map.get(parent.getId());
                parentDTO.getReplies().add(completeComments(comment));
            } else {
                topLevelComments.add(map.get(comment.getId()));
            }
        }

        // 정렬
        topLevelComments.sort(Comparator.comparing(CommentsResponseDTO.CommentsListDTO::getCreatedAt));
        topLevelComments.forEach(c ->
                c.getReplies().sort(Comparator.comparing(CommentsResponseDTO.CommentsDTO::getCreatedAt))
        );
        return topLevelComments;
    }

    public static CommentsResponseDTO.CommentsResultDTO toCommentsResult(Comments newComments, Post post) {
        return CommentsResponseDTO.CommentsResultDTO.builder()
                .commentsId(newComments.getId())
                .postId(post.getId())
                .createdAt(LocalDateTime.now())
                .build();
    }
}
