package project.ping.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import project.ping.apiPayload.ApiResponse;
import project.ping.dto.JwtDTO;
import project.ping.dto.MemberRequestDTO;
import project.ping.dto.MemberResponseDTO;
import project.ping.security.auth.MemberDetail;
import project.ping.service.MemberService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
@Transactional
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/join")
    @Operation(summary = "일반 회원가입 API")
    public ApiResponse<?> join(@RequestBody MemberRequestDTO.MemberJoinDTO request){
        return ApiResponse.onSuccess(memberService.joinMember(request));
    }

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

    @PostMapping("/login")
    @Operation(summary = "일반 로그인 API")
    public ResponseEntity<ApiResponse<?>> login(@RequestBody MemberRequestDTO.MemberJoinDTO request){
        HttpHeaders result = memberService.loginMember(request);
        return ResponseEntity.ok().headers(result).body(ApiResponse.onSuccess(null));
    }

    @GetMapping("/myPage")
    @Operation(summary = "마이페이지 조회 API")
    public ApiResponse<?> myPage(@AuthenticationPrincipal MemberDetail memberDetail){
        return ApiResponse.onSuccess(memberService.getMyPage(memberDetail));
    }

}
