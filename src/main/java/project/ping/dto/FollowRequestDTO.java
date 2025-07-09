package project.ping.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class FollowRequestDTO {

    @Getter
    public static class followDTO {
        private Long followerId;
        private Long followingId;
    }

    @Getter
    public static class unfollowDTO{
        private Long followingId;
    }
}
