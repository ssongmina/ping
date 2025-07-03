package project.ping.converter;

import project.ping.domain.Member;
import project.ping.domain.enums.MemberStatus;
import project.ping.dto.MemberRequestDTO;
import project.ping.dto.MemberResponseDTO;

import java.time.LocalDateTime;

public class MemberConverter {

    public static Member toMember(MemberRequestDTO.MemberJoinDTO request, String name){
        return Member.builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .memberStatus(MemberStatus.ACTIVE)
                .nickname(name)
                .build();
    }

    public static MemberResponseDTO.MemberJoinResultDTO toJoinResult(Member member){
        return new MemberResponseDTO.MemberJoinResultDTO().builder()
                .id(member.getId())
                .nickname(member.getNickname())
                .createdAt(LocalDateTime.now())
                .build();
    }


}
