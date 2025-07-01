package project.ping.domain;

import jakarta.persistence.*;
import lombok.Getter;
import project.ping.domain.common.BaseEntity;
import project.ping.domain.enums.MemberStatus;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String password;

    private String nickname;

    private MemberStatus memberStatus;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<Post> postList = new ArrayList<>();

    // 내가 누구를 팔로우하는 중인지
    @OneToMany(mappedBy = "follower", fetch = FetchType.LAZY)
    private List<Follow> followingList = new ArrayList<>();

    // 누가 나를 팔로우하는 중인지
    @OneToMany(mappedBy = "following", fetch = FetchType.LAZY)
    private List<Follow> followerList = new ArrayList<>();

}
