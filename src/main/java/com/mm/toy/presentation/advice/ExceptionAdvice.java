package com.mm.toy.presentation.advice;

import com.mm.toy.presentation.payload.code.Reason;
import com.mm.toy.presentation.payload.exception.BoardHandler;
import com.mm.toy.presentation.payload.exception.CommentHandler;
import com.mm.toy.presentation.payload.exception.LikeHandler;
import com.mm.toy.presentation.payload.exception.UserHandler;
import jdk.jshell.spi.ExecutionControl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice(annotations = RestController.class)
public class ExceptionAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserHandler.class)
    public ResponseEntity<Reason> handleUserException(UserHandler ex) {
        // return () -> (Reason, HttpStatus): 상태코드와 메세지를 함께 전달
        return new ResponseEntity<>(ex.getErrorReasonHttpStatus(), ex.getErrorReasonHttpStatus().getHttpStatus());
    }

    @ExceptionHandler(BoardHandler.class)
    public ResponseEntity<Reason> handleBoardException(BoardHandler ex) {
        return new ResponseEntity<>(ex.getErrorReasonHttpStatus(), ex.getErrorReasonHttpStatus().getHttpStatus());
    }

    @ExceptionHandler(LikeHandler.class)
    public ResponseEntity<Reason> handleLikeException(LikeHandler ex) {
        return new ResponseEntity<>(ex.getErrorReasonHttpStatus(), ex.getErrorReasonHttpStatus().getHttpStatus());
    }

    @ExceptionHandler(CommentHandler.class)
    public ResponseEntity<Reason> handleCommentException(CommentHandler ex) {
        return new ResponseEntity<>(ex.getErrorReasonHttpStatus(), ex.getErrorReasonHttpStatus().getHttpStatus());
    }
}
