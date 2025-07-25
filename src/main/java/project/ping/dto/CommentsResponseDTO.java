package project.ping.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

public class CommentsResponseDTO {

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CommentsDTO {
        private Long commentsId;
        private String nickname;
        private String content;
        private LocalDateTime createdAt;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CommentsListDTO{
        private Long commentsId;
        private String nickname;
        private String content;
        private LocalDateTime createdAt;
        List<CommentsDTO> replies;
    }

}
