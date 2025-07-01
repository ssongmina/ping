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
    ;

    private final HttpStatus status;
    private final String code;
    private final String message;
}