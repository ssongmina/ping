package project.ping.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import project.ping.apiPayload.ApiResponse;
import project.ping.dto.CommentsRequestDTO;
import project.ping.security.auth.MemberDetail;
import project.ping.service.CommentsService;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentsController {

    private final CommentsService commentsService;

    @PostMapping
    @Operation(summary = "댓글 작성하기 API")
    public ApiResponse<?> write(@AuthenticationPrincipal MemberDetail memberDetail,
                                @RequestBody CommentsRequestDTO.WriteCommentsDTO request){
        return ApiResponse.onSuccess(commentsService.writeComments(memberDetail, request));
    }

    @PatchMapping
    @Operation(summary = "댓글 수정하기 API")
    public ApiResponse<?> update(@AuthenticationPrincipal MemberDetail memberDetail,
                                 @RequestBody CommentsRequestDTO.UpdateCommentsDTO request){
        return ApiResponse.onSuccess(commentsService.updateComments(memberDetail, request));
    }

    @DeleteMapping
    @Operation(summary = "댓글 삭제하기 API")
    public ApiResponse<?> delete(@AuthenticationPrincipal MemberDetail memberDetail, @RequestParam Long commentsId){
        return ApiResponse.onSuccess(commentsService.deleteComments(memberDetail, commentsId));
    }

    @GetMapping
    @Operation(summary = "댓글 조회하기 API")
    public ApiResponse<?> get(@RequestParam Long postId){
        return ApiResponse.onSuccess(commentsService.getComments(postId));
    }

}
