package project.ping.converter;

import project.ping.domain.Member;
import project.ping.domain.Post;
import project.ping.dto.PostRequestDTO;
import project.ping.dto.PostResponseDTO;

import java.util.List;
import java.util.stream.Collectors;

public class PostConverter {

    public static Post toPost(Member member, PostRequestDTO.postDTO request){
        return Post.builder()
                .member(member)
                .content(request.getContent())
                .build();
    }

    public static PostResponseDTO.getPostListDTO toPostList(List<Post> posts) {
        List<PostResponseDTO.getPostDTO> lists = posts.stream().
                map(PostConverter::toPostDTO).collect(Collectors.toList());
        return PostResponseDTO.getPostListDTO.builder()
                .lists(lists)
                .build();
    }

    public static PostResponseDTO.getPostDTO toPostDTO(Post post){
        return PostResponseDTO.getPostDTO.builder()
                .id(post.getId())
                .content(post.getContent())
                .build();
    }


}
