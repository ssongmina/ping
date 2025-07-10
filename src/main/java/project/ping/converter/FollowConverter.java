package project.ping.converter;

import project.ping.domain.Follow;
import project.ping.domain.Member;
import project.ping.dto.FollowResponseDTO;

import java.util.List;
import java.util.stream.Collectors;

public class FollowConverter {

    public static Follow toFollow(Member follower, Member following){
        return Follow.builder()
                .follower(follower)
                .following(following)
                .build();
    }

    public static FollowResponseDTO.FollowingListDTO toFollowList(List<Follow> follows) {
        List<FollowResponseDTO.FollowingDTO> lists = follows.stream()
                .map(FollowConverter::toFollowingDTO).collect(Collectors.toList());
        return FollowResponseDTO.FollowingListDTO.builder()
                .count(lists.stream().count())
                .followingDTOList(lists)
                .build();
    }

    public static FollowResponseDTO.FollowingDTO toFollowingDTO(Follow follow){
        Member member = follow.getFollowing();
        return FollowResponseDTO.FollowingDTO.builder()
                .followingId(member.getId())
                .followingName(member.getNickname())
                .build();
    }
}
