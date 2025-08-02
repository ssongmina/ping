package project.ping.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import project.ping.apiPayload.ApiResponse;
import project.ping.domain.Post;
import project.ping.dto.MemberRequestDTO;
import project.ping.dto.PostRequestDTO;
import project.ping.security.auth.MemberDetail;
import project.ping.service.PostService;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    @Operation(summary = "게시물을 작성하는 API")
    public ApiResponse<?> writePost(@AuthenticationPrincipal MemberDetail memberDetail,
                                    @RequestBody PostRequestDTO.postDTO request){
        return ApiResponse.onSuccess(postService.write(memberDetail.getMember(), request));
    }

    @PatchMapping("/{postId}")
    @Operation(summary = "게시물을 수정하는 API")
    public ApiResponse<?> updatePost(@AuthenticationPrincipal MemberDetail memberDetail,
                                     @PathVariable Long postId,
                                     @RequestBody PostRequestDTO.postDTO request){
        return ApiResponse.onSuccess(postService.update(memberDetail, postId, request));
    }

    @DeleteMapping("/{postId}")
    @Operation(summary = "게시물을 삭제하는 API")
    public ApiResponse<?> deletePost(@AuthenticationPrincipal MemberDetail memberDetail,
                                     @PathVariable Long postId){
        return ApiResponse.onSuccess(postService.delete(memberDetail, postId));
    }

    @GetMapping
    @Operation(summary = "피드를 조회하는 API")
    public ApiResponse<?> getPosts(@AuthenticationPrincipal MemberDetail memberDetail){
        return ApiResponse.onSuccess(postService.get(memberDetail));
    }

    @GetMapping("/{memberId}")
    @Operation(summary = "특정 회원이 작성한 게시글을 조회하는 API")
    public ApiResponse<?> getPostsMember(@PathVariable Long memberId){
        return ApiResponse.onSuccess(postService.getMemberPosts(memberId));
    }

}
