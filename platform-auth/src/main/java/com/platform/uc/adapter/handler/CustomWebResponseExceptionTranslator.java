package com.platform.uc.adapter.handler;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.DefaultThrowableAnalyzer;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InsufficientScopeException;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.web.util.ThrowableAnalyzer;
import org.springframework.web.HttpRequestMethodNotSupportedException;

/**
 * 异常梳理
 * @author hao.yan
 */
public class CustomWebResponseExceptionTranslator implements WebResponseExceptionTranslator<OAuth2Exception> {

    private final ThrowableAnalyzer throwableAnalyzer = new DefaultThrowableAnalyzer();

    @Override
    public ResponseEntity<OAuth2Exception> translate(Exception e) {
//
//        ResponseEntity<OAuth2Exception> responseEntity = super.translate(e);
//        OAuth2Exception body = responseEntity.getBody();
//        HttpHeaders headers = new HttpHeaders();
//        headers.setAll(responseEntity.getHeaders().toSingleValueMap());
//        headers.set("Access-Control-Allow-Origin", "*");
//        headers.set("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT");
//        headers.set("Access-Control-Max-Age", "3600");
//        headers.set("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
//        return new ResponseEntity<>(body, headers, responseEntity.getStatusCode());

//        // Try to extract a SpringSecurityException from the stacktrace
//        Throwable[] causeChain = throwableAnalyzer.determineCauseChain(e);
//
//        Exception ase = (AuthenticationException) throwableAnalyzer.getFirstThrowableOfType(AuthenticationException.class,
//                causeChain);
//        if (ase != null) {
//            return handleOAuth2Exception(new UnauthorizedException(e.getMessage(), e));
//        }
//
//        ase = (AccessDeniedException) throwableAnalyzer
//                .getFirstThrowableOfType(AccessDeniedException.class, causeChain);
//        if (ase != null) {
//            return handleOAuth2Exception(new ForbiddenException(ase.getMessage(), ase));
//        }
//
//        ase = (InvalidGrantException) throwableAnalyzer
//                .getFirstThrowableOfType(InvalidGrantException.class, causeChain);
//        if (ase != null) {
//            return handleOAuth2Exception(new InvalidException(ase.getMessage(), ase));
//        }
//
//        ase = (HttpRequestMethodNotSupportedException) throwableAnalyzer
//                .getFirstThrowableOfType(HttpRequestMethodNotSupportedException.class, causeChain);
//        if (ase != null) {
//            return this.handleOAuth2Exception(new MethodNotAllowed(ase.getMessage(), ase));
//        }
//
//        ase = (OAuth2Exception) throwableAnalyzer.getFirstThrowableOfType(
//                OAuth2Exception.class, causeChain);
//
//        if (ase != null) {
//            return handleOAuth2Exception((OAuth2Exception) ase);
//        }
//
//        return handleOAuth2Exception(new ServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), e));
        return null;
    }

//    private ResponseEntity<CustomAuth2Exception> handleOAuth2Exception(OAuth2Exception e) {
//
//        int status = e.getHttpErrorCode();
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("Cache-Control", "no-store");
//        headers.set("Pragma", "no-cache");
//        if (status == HttpStatus.UNAUTHORIZED.value() || (e instanceof InsufficientScopeException)) {
//            headers.set("WWW-Authenticate", String.format("%s %s", OAuth2AccessToken.BEARER_TYPE, e.getSummary()));
//        }
//
//        // 客户端异常直接返回客户端,不然无法解析
//        return new ResponseEntity<>(new CustomAuth2Exception(e.getOAuth2ErrorCode(), e.getMessage()), headers,
//                HttpStatus.valueOf(status));
//    }
//
//    private static class UnauthorizedException extends OAuth2Exception {
//        public UnauthorizedException(String msg, Throwable t) {
//            super(msg, t);
//        }
//
//        @Override
//        public String getOAuth2ErrorCode() {
//            return "unauthorized";
//        }
//
//        @Override
//        public int getHttpErrorCode() {
//            return 401;
//        }
//    }
//
//    private static class ForbiddenException extends OAuth2Exception {
//        public ForbiddenException(String msg, Throwable t) {
//            super(msg, t);
//        }
//
//        @Override
//        public String getOAuth2ErrorCode() {
//            return "access_denied";
//        }
//
//        @Override
//        public int getHttpErrorCode() {
//            return 403;
//        }
//    }
//
//    private static class MethodNotAllowed extends OAuth2Exception {
//        public MethodNotAllowed(String msg, Throwable t) {
//            super(msg, t);
//        }
//
//        @Override
//        public String getOAuth2ErrorCode() {
//            return "method_not_allowed";
//        }
//
//        @Override
//        public int getHttpErrorCode() {
//            return 405;
//        }
//    }
//
//    private static class ServerErrorException extends OAuth2Exception {
//        public ServerErrorException(String msg, Throwable t) {
//            super(msg, t);
//        }
//
//        @Override
//        public String getOAuth2ErrorCode() {
//            return "server_error";
//        }
//
//        @Override
//        public int getHttpErrorCode() {
//            return 500;
//        }
//    }
//
//    private static class InvalidException extends OAuth2Exception {
//        public InvalidException(String msg, Throwable t) {
//            super(msg, t);
//        }
//
//        @Override
//        public String getOAuth2ErrorCode() {
//            return "用户名或密码错误";
//        }
//
//        @Override
//        public int getHttpErrorCode() {
//            return 403;
//        }
//    }

}
