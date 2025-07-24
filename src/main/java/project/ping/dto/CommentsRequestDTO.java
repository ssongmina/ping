package project.ping.dto;

import lombok.Getter;

public class CommentsRequestDTO {

    @Getter
    public static class WriteCommentsDTO{
        private Long postId;
        private Long commentId; // 부모댓글확인용
        private String context;
    }


}
