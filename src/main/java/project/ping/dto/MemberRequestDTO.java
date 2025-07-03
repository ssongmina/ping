package project.ping.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

public class MemberRequestDTO {

    @Getter
    public static class MemberJoinDTO{

        @NotBlank(message = "이메일은 필수 입력 값입니다")
        @Pattern(regexp = "^[\\w!#$%&'*+/=?`{|}~^.-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$",
                    message = "이메일 형식이 올바르지 않습니다.")
        String email;

        @NotBlank(message = "비밀번호는 필수 입력 값입니다")
        String password;
    }
}
