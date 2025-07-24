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
        commentsService.writeComments(memberDetail, request);
        return ApiResponse.onSuccess(null);
    }

    @PatchMapping
    @Operation(summary = "댓글 수정하기 API")
    public ApiResponse<?> update(@AuthenticationPrincipal MemberDetail memberDetail,
                                 @RequestBody CommentsRequestDTO.UpdateCommentsDTO request){
        return ApiResponse.onSuccess(commentsService.updateComments(memberDetail, request));
    }
}
