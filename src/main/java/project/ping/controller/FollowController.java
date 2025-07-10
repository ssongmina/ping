package project.ping.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import project.ping.apiPayload.ApiResponse;
import project.ping.dto.FollowRequestDTO;
import project.ping.security.auth.MemberDetail;
import project.ping.service.FollowService;

@RestController
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;

    @PostMapping("/follow")
    @Operation(summary = "팔로우 API")
    public ApiResponse<?> follow(@AuthenticationPrincipal MemberDetail memberDetail,
                                 @RequestBody FollowRequestDTO.followDTO request){
        followService.followYou(memberDetail, request);
        return ApiResponse.onSuccess(null);
    }

    @PostMapping("/unfollow")
    @Operation(summary = "언팔로우 API")
    public ApiResponse<?> unfollow(@AuthenticationPrincipal MemberDetail memberDetail,
                                   @RequestBody FollowRequestDTO.followDTO request){
        followService.unfollowYou(memberDetail, request);
        return ApiResponse.onSuccess(null);
    }

    @GetMapping("/following/list")
    @Operation(summary = "팔로잉을 조회하는 API")
    public ApiResponse<?> getFollowingList(@AuthenticationPrincipal MemberDetail memberDetail){
        return ApiResponse.onSuccess(followService.getFollowings(memberDetail));
    }
}
