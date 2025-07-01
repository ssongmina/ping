package project.ping.domain;

import jakarta.persistence.*;
import lombok.Getter;
import project.ping.domain.common.BaseEntity;

@Entity
@Getter
public class PostImage extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "POST_ID")
    private Post post;
}
