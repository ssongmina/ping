package project.ping.service;

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
public class FollowService {

    private final MemberRepository memberRepository;
    private final FollowRepository followRepository;

    // 팔로우 API : 내가 누군가를 따른다 / 팔로워 : 나, 팔로잉 : 다른 이
    public void followYou(MemberDetail memberDetail, FollowRequestDTO.followDTO request) {
        Member member = memberDetail.getMember();
        if(request.getFollowerId() != member.getId()){
            throw new GeneralException(ErrorStatus.NOT_CORRECT);
        }
        Member following = memberRepository.findById(request.getFollowingId()).
                orElseThrow(() -> new GeneralException(ErrorStatus.NOT_EXSIT_FOLLOWING_MEMBER));
        if(followRepository.existsByFollowerAndFollowing(member, following)){
            throw new GeneralException(ErrorStatus.ALREADY_FOLLOW);
        }
        Follow follow = FollowConverter.toFollow(member, following);
        followRepository.save(follow);
    }

}