package project.ping.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class FollowResponseDTO {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FollowingListDTO{
        private Long count;
        private List<FollowingDTO> followingDTOList;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FollowingDTO{
        private Long followingId;
        private String followingName;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FollowerListDTO{
        private Long count;
        private List<FollowerDTO> followerDTOList;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FollowerDTO{
        private Long followerId;
        private String followerName;
    }
}
