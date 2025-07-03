package project.ping.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import project.ping.apiPayload.ApiResponse;
import project.ping.dto.MemberRequestDTO;
import project.ping.dto.MemberResponseDTO;
import project.ping.service.MemberService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/join")
    @Operation(summary = "일반 회원가입 API")
    public ApiResponse<?> join(@RequestBody MemberRequestDTO.MemberJoinDTO request){
        MemberResponseDTO.MemberJoinResultDTO result = memberService.joinMember(request);
        return ApiResponse.onSuccess(result);
    }

    // 이메일 인증 로직
    @PostMapping("/email")
    @Operation(summary = "이메일 인증번호 전송 API")
    public ApiResponse<?> sendEmail(@RequestParam String email){
        memberService.sendMail(email);
        return ApiResponse.onSuccess(null);
    }

    @GetMapping("/email/verify")
    @Operation(summary = "이메일 인증 확인 API")
    public ApiResponse<?> verifyEmail(@RequestParam String email, @RequestParam String code){
        memberService.verifyMail(email, code);
        return ApiResponse.onSuccess(null);
    }
}
