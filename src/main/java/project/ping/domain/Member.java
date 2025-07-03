package project.ping.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import project.ping.domain.common.BaseEntity;
import project.ping.domain.enums.MemberStatus;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String password;

    private String nickname;

    @Enumerated(EnumType.STRING)
    private MemberStatus memberStatus;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<Post> postList = new ArrayList<>();

    // 내가 누구를 팔로우하는 중인지
    @OneToMany(mappedBy = "follower", fetch = FetchType.LAZY)
    private List<Follow> followingList = new ArrayList<>();

    // 누가 나를 팔로우하는 중인지
    @OneToMany(mappedBy = "following", fetch = FetchType.LAZY)
    private List<Follow> followerList = new ArrayList<>();

    // 비밀번호 암호화
    public void encodePassword(String password) {
        this.password = password;
    }

}
