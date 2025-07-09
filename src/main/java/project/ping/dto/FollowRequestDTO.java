package project.ping.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class FollowRequestDTO {

    @Getter
    public static class followDTO {
        private Long followingId;
    }

}
