package project.ping.domain;

import jakarta.persistence.*;
import project.ping.domain.common.BaseEntity;

import javax.xml.stream.events.Comment;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Comments extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "POST_ID")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COMMENT_ID")
    private Comments parent;

    @OneToMany(mappedBy = "parent")
    private List<Comments> children = new ArrayList<>();

}
