package project.ping.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.ping.apiPayload.exception.GeneralException;
import project.ping.apiPayload.status.ErrorStatus;
import project.ping.converter.PostConverter;
import project.ping.domain.Member;
import project.ping.domain.Post;
import project.ping.dto.PostRequestDTO;
import project.ping.repository.MemberRepository;
import project.ping.repository.PostRepository;
import project.ping.security.auth.MemberDetail;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    public void write(Member member, PostRequestDTO.postDTO request){
        Post post = PostConverter.toPost(member, request);
        postRepository.save(post);
    }

    public void update(MemberDetail memberDetail, Long postId, PostRequestDTO.postDTO request) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new GeneralException(ErrorStatus.NOT_EXIST_POST));
        if(post.getMember().getId() != memberDetail.getMember().getId()){
            throw new GeneralException(ErrorStatus.NOT_YOUR_POST);
        }
        post.modifyPost(request.getContent());
    }

    public void delete(MemberDetail memberDetail, Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new GeneralException(ErrorStatus.NOT_EXIST_POST));
        if(post.getMember().getId() != memberDetail.getMember().getId()){
            throw  new GeneralException(ErrorStatus.NOT_YOUR_POST);
        }
        postRepository.delete(post);
    }
}
