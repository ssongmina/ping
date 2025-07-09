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
import project.ping.repository.FollowRepository;
import project.ping.repository.MemberRepository;
import project.ping.security.auth.MemberDetail;

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
        if(followRepository.existsByFollowerAndFollowing(follower, following)){
            throw new GeneralException(ErrorStatus.ALREADY_FOLLOW);
        }
        Follow follow = FollowConverter.toFollow(follower, following);
        followRepository.save(follow);
    }

    // 언팔로우 API
    public void unfollowYou(MemberDetail memberDetail, FollowRequestDTO.followDTO request) {
        Member member = memberRepository.findById(request.getFollowingId())
                .orElseThrow(() -> new GeneralException(ErrorStatus.NOT_EXIST_MEMBER));
        Follow follow = followRepository.findByFollowerAndFollowing(memberDetail.getMember(), member).
                orElseThrow(() -> new GeneralException(ErrorStatus.NOT_EXIST_FOLLOW));
        followRepository.delete(follow);
    }
}