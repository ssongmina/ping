package project.ping.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.ping.apiPayload.exception.GeneralException;
import project.ping.apiPayload.status.ErrorStatus;
import project.ping.converter.FollowConverter;
import project.ping.domain.Follow;
import project.ping.domain.Member;
import project.ping.dto.FollowRequestDTO;
import project.ping.dto.FollowResponseDTO;
import project.ping.repository.FollowRepository;
import project.ping.repository.MemberRepository;
import project.ping.security.auth.MemberDetail;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class FollowService {

    private final MemberRepository memberRepository;
    private final FollowRepository followRepository;

    // 팔로우 API : 내가 누군가를 따른다 / 팔로워 : 나, 팔로잉 : 다른 이
    public void followYou(MemberDetail memberDetail, FollowRequestDTO.followDTO request) {
        Member follower = memberDetail.getMember();
        Member following = memberRepository.findById(request.getFollowingId()).
                orElseThrow(() -> new GeneralException(ErrorStatus.NOT_EXIST_MEMBER));
        if(follower.getId() == following.getId()) {
            throw new GeneralException(ErrorStatus.NOT_SELF_FOLLOW);
        }
        if(followRepository.existsByFollowerAndFollowing(follower, following)){
            throw new GeneralException(ErrorStatus.ALREADY_FOLLOW);
        }
        Follow follow = FollowConverter.toFollow(follower, following);
        followRepository.save(follow);
    }

    // 언팔로우 API
    public void unfollowYou(MemberDetail memberDetail, FollowRequestDTO.followDTO request) {
        Member follower = memberDetail.getMember();
        Member following = memberRepository.findById(request.getFollowingId())
                .orElseThrow(() -> new GeneralException(ErrorStatus.NOT_EXIST_MEMBER));
        if(follower.getId() == following.getId()) {
            throw new GeneralException(ErrorStatus.NOT_SELF_UNFOLLOW);
        }
        Follow follow = followRepository.findByFollowerAndFollowing(follower, following).
                orElseThrow(() -> new GeneralException(ErrorStatus.NOT_EXIST_FOLLOW));
        followRepository.delete(follow);
    }

    // 팔로잉을 조회하는 API
    public FollowResponseDTO.FollowingListDTO getFollowings(MemberDetail memberDetail) {
        Member follower = memberDetail.getMember();
        List<Follow> follows = followRepository.findByFollower(follower);
        // 회원의 아이디, 이름 정보를 가져오기
        return FollowConverter.toFollowingList(follows);
    }

    // 팔로워를 조회하는 API
    public FollowResponseDTO.FollowerListDTO getFollowers(MemberDetail memberDetail) {
        Member member = memberDetail.getMember();
        List<Follow> follows = followRepository.findByFollowing(member);
        return FollowConverter.toFollowerList(follows);
    }
}