package project.ping.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import project.ping.apiPayload.ApiResponse;
import project.ping.security.auth.MemberDetail;
import project.ping.service.LikesService;

@RestController
@RequestMapping("/likes")
@RequiredArgsConstructor
public class LikesController {

    private final LikesService likesService;

    @PostMapping
    @Operation(summary = "좋아요 누르기 API")
    public ApiResponse<?> pushLikes(@AuthenticationPrincipal MemberDetail memberDetail, @RequestParam Long postId){
        likesService.push(memberDetail, postId);
        return ApiResponse.onSuccess(null);
    }
}
