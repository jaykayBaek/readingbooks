package com.readingbooks.web.controller;

import com.readingbooks.web.exception.base.NotFoundException;
import com.readingbooks.web.exception.base.PresentException;
import com.readingbooks.web.exception.login.NotLoginException;
import com.readingbooks.web.exception.member.MemberException;
import com.readingbooks.web.exception.review.ReviewException;
import com.readingbooks.web.exception.wishlist.WishlistException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ControllerExceptionAdvice {

    @ExceptionHandler(PresentException.class)
    public ResponseEntity<Object> handlePresentException(Exception e){
        log.info("예외 발생 - PresentException : ", e.getMessage());

        BaseResponse response = new BaseResponse(HttpStatus.CONFLICT, e.getMessage(), false);
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(response);

    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handlerNotFoundException(Exception e){
        log.info("예외 발생 - NotFoundException : ", e.getMessage());

        BaseResponse response = new BaseResponse(HttpStatus.NOT_FOUND, e.getMessage(), false);
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(response);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handlerIllegalArgumentException(Exception e){
        log.info("예외 발생 - IllegalArgumentException : ", e.getMessage());

        BaseResponse response = new BaseResponse(HttpStatus.BAD_REQUEST, e.getMessage(), false);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

    @ExceptionHandler(WishlistException.class)
    public ResponseEntity<Object> handlerWishlistException(Exception e){
        log.info("예외 발생 - WishlistException : ", e.getMessage());

        BaseResponse response = new BaseResponse(HttpStatus.FORBIDDEN, e.getMessage(), false);
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(response);
    }

    @ExceptionHandler(NotLoginException.class)
    public ResponseEntity<Object> handlerNotLoginException(Exception e){
        log.info("예외 발생 - NotLoginException : ", e.getMessage());

        BaseResponse response = new BaseResponse(HttpStatus.UNAUTHORIZED, e.getMessage(), false);
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(response);
    }

    @ExceptionHandler(ReviewException.class)
    public ResponseEntity<Object> handlerReviewException(ReviewException e){
        log.info("예외 발생 - ReviewException : ", e.getMessage());

        BaseResponse response = new BaseResponse(e.getStatus(), e.getMessage(), false);
        return ResponseEntity
                .status(e.getStatus())
                .body(response);
    }

    @ExceptionHandler(MemberException.class)
    public ResponseEntity<Object> handlerMemberException(Exception e){
        log.info("예외 발생 - MemberException : ", e.getMessage());

        BaseResponse response = new BaseResponse(HttpStatus.BAD_REQUEST, e.getMessage(), false);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }
}
