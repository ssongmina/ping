package project.ping.domain;

import jakarta.persistence.*;
import lombok.*;
import project.ping.domain.common.BaseEntity;


@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Follow extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 팔로우 하는 사람
    @ManyToOne
    @JoinColumn(name = "FOLLOWER_ID")
    private Member follower;

    // 팔로우 당하는 사람
    @ManyToOne
    @JoinColumn(name = "FOLLOWING_ID")
    private Member following;

}
