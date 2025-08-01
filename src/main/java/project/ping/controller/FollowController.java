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
    @Operation(summary = "팔로우 API(나 -> 다른 회원)")
    public ApiResponse<?> follow(@AuthenticationPrincipal MemberDetail memberDetail,
                                 @RequestBody FollowRequestDTO.followDTO request){
        return ApiResponse.onSuccess(followService.followYou(memberDetail, request));
    }

    @PostMapping("/unfollow")
    @Operation(summary = "언팔로우 API(나 -> 다른 회원)")
    public ApiResponse<?> unfollow(@AuthenticationPrincipal MemberDetail memberDetail,
                                   @RequestBody FollowRequestDTO.followDTO request){
        return ApiResponse.onSuccess(followService.unfollowYou(memberDetail, request));
    }

    @GetMapping("/following/list")
    @Operation(summary = "팔로잉을 조회하는 API")
    public ApiResponse<?> getFollowingList(@AuthenticationPrincipal MemberDetail memberDetail){
        return ApiResponse.onSuccess(followService.getFollowings(memberDetail));
    }

    @GetMapping("/follower/list")
    @Operation(summary = "팔로워를 조회하는 API")
    public ApiResponse<?> getFollowerList(@AuthenticationPrincipal MemberDetail memberDetail){
        return ApiResponse.onSuccess(followService.getFollowers(memberDetail));
    }
}
