package com.devchw.gukmo.config.response;

import lombok.Getter;

/**
 * 상태 코드 관리
 */
@Getter
public enum BaseResponseStatus {
    /**
     * 1000 : 요청 성공
     */
    SUCCESS(true, 1000, "요청에 성공하였습니다."),

    /**
     * 2000 : Request 오류
     */
    BAD_REQUEST(false, 2001, "잘못된 요청입니다."),

    /**
     * 2500 : Request 성공
     */
    FILE_UPLOAD_SUCCESS(true, 2500, "파일 업로드에 성공하였습니다."),
    SEND_EMAIL_SUCCESS(true, 2501, "이메일 발송에 성공하였습니다."),
    MEMBER_INFO_CHANGE_SUCCESS(true, 2502, "회원 정보 변경에 성공하였습니다."),
    MEMBER_DELETE_SUCCESS(true, 2503, "회원 삭제에 성공하였습니다."),
    PASSWORD_CHANGE_SUCCESS(true, 2504, "비밀번호 변경에 성공하였습니다."),
    NAVER_LOGIN_SUCCESS(true, 2505, "네이버 로그인에 성공하였습니다."),
    BOARD_LIKE_SUCCESS(true, 2506, "게시글을 추천하였습니다."),
    COMMENT_LIKE_SUCCESS(true, 2507, "댓글을 추천하였습니다."),
    COMMENT_DELETE_SUCCESS(true, 2508, "댓글 삭제 성공하였습니다."),

    /**
     * 3000 : Response 오류
     */
    VALIDATED_ERROR(false, 3000, "VALIDATED_ERROR"), // @Valid 예외 처리
    NOT_FOUND_MEMBER(false, 3001, "없는 회원입니다."),
    NOT_FOUND_BOARD(false, 3002, "없는 게시물입니다."),
    NOT_FOUND_COMMENT(false, 3003, "없는 댓글입니다."),
    NOT_FOUND_BOARD_HASHTAG(false, 3004, "해당 게시물에 달린 해시태그가 없습니다."),
    NOT_FOUND_ACTIVITY(false, 3005, "활동내역을 찾을 수 없습니다."),
    NOT_FOUND_ADVERTISEMENT(false, 3006, "없는 광고입니다."),


    /**
     * 4000 : Database, Server 오류
     */
    INTERNAL_SERVER_ERROR(false, 4000, "서버 오류입니다"),
    JSON_PROCESSING_ERROR(false, 4001, "JSON을 처리하는 과정 중 오류가 발생했습니다."),
    ENCODING_ERROR(false, 4002, "인코딩을 처리하는 과정 중 오류가 발생했습니다.");

    private final boolean success;
    private final int status;
    private final String message;

    BaseResponseStatus(boolean success, int code, String message){
        this.success = success;
        this.status = code;
        this.message = message;
    }
}
