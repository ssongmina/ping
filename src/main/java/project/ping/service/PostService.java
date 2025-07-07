package project.ping.service;

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

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    public void write(Member member, PostRequestDTO.postDTO request){
        Post post = PostConverter.toPost(member, request);
        postRepository.save(post);
    }
}
