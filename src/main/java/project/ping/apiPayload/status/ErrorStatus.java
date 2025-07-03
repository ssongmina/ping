package project.ping.apiPayload.status;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorStatus {

    // 가장 일반적인 응답
    _INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON500", "서버 에러, 관리자에게 문의 바랍니다."),
    _BAD_REQUEST(HttpStatus.BAD_REQUEST,"COMMON400","잘못된 요청입니다."),
    _UNAUTHORIZED(HttpStatus.UNAUTHORIZED,"COMMON401","인증이 필요합니다."),
    _FORBIDDEN(HttpStatus.FORBIDDEN, "COMMON403", "금지된 요청입니다."),

    // 가게 관련 응답
    NOT_EXIST_STORE(HttpStatus.BAD_REQUEST, "STORE4001", "존재하지 않는 가게입니다"),
    NOT_EXIST_MENU(HttpStatus.BAD_REQUEST, "STORE4002", "해당 가게에 메뉴가 존재하지 않습니다"),

    // 회원 관련 응답
    EXIST_EMAIL(HttpStatus.BAD_REQUEST, "MEMBER4001", "이미 회원가입한 이메일입니다. 다른 이메일로 가입하세요"),
    WRONG_EMAIL_VERIFICATOIN(HttpStatus.BAD_REQUEST, "MEMBER4002", "이메일 인증번호가 틀렸습니다. 다시 입력해주세요"),
    EXPIRE_EMAIL_CODE(HttpStatus.BAD_REQUEST, "MEMBER4003", "이메일 인증번호가 만료되었습니다. 다시 인증번호를 받으세요"),
    ;

    private final HttpStatus status;
    private final String code;
    private final String message;
}