package project.ping.apiPayload.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import project.ping.apiPayload.status.ErrorStatus;

@Getter
@AllArgsConstructor
public class GeneralException extends RuntimeException {

    private ErrorStatus errorStatus;

}
