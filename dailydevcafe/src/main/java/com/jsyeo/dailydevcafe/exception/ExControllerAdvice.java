package com.jsyeo.dailydevcafe.handler;

import com.jsyeo.dailydevcafe.dto.response.ResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity badRequestHandle(IllegalArgumentException e) {
        ResponseDto<Object> responseDto = new ResponseDto<>();
        responseDto.setCode(HttpStatus.BAD_REQUEST.value());
        responseDto.setMessage("잘못된 요청입니다.");
        responseDto.setData(e.getMessage());

        return ResponseEntity.badRequest().body(responseDto);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity bindExHandle(MethodArgumentNotValidException e) {
        ResponseDto<Object> responseDto = new ResponseDto<>();
        responseDto.setCode(HttpStatus.BAD_REQUEST.value());
        responseDto.setMessage("잘못된 요청입니다.");
        responseDto.setData(e.getMessage());

        return ResponseEntity.badRequest().body(responseDto);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public ResponseEntity serverErrorHandle(Exception e) {
        ResponseDto<Object> responseDto = new ResponseDto<>();
        responseDto.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        responseDto.setMessage("서버 오류가 발생했습니다.");
        responseDto.setData(e.getMessage());

        return ResponseEntity.internalServerError().body(responseDto);
    }
}
