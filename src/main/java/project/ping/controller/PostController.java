package project.ping.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.ping.apiPayload.ApiResponse;
import project.ping.domain.Post;
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
        postService.write(memberDetail.getMember(), request);
        return ApiResponse.onSuccess(null);
    }
}
