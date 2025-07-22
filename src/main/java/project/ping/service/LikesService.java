package project.ping.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import project.ping.apiPayload.exception.GeneralException;
import project.ping.apiPayload.status.ErrorStatus;
import project.ping.converter.LikesConverter;
import project.ping.domain.Likes;
import project.ping.domain.Member;
import project.ping.domain.Post;
import project.ping.repository.LikesRepository;
import project.ping.repository.PostRepository;
import project.ping.security.auth.MemberDetail;

@Service
@Transactional
@RequiredArgsConstructor
public class LikesService {

    private final LikesRepository likesRepository;
    private final PostRepository postRepository;

    // 좋아요 누르기
    public void push(MemberDetail memberDetail, Long postId) {
        Member member = memberDetail.getMember();
        Post post = postRepository.findById(postId)
                .orElseThrow(()-> new GeneralException(ErrorStatus.NOT_EXIST_POST));
        // 이미 눌러져 있는지 확인하기
        Likes likes = likesRepository.findByMemberAndPost(member, post);
        if(likes == null){
            Likes newLikes = LikesConverter.toLikes(member, post);
            likesRepository.save(newLikes);
        }
        else{
            likesRepository.delete(likes);
        }
    }

}
