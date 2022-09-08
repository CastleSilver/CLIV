package com.ssafy.crafts.api.service;

import com.ssafy.crafts.api.response.ClassRoomResponse;

/**
 * @FileName : ClassRoomService
 * @작성자 : 허성은
 * @Class 설명 : 화상 관련 비즈니스 처리 로직을 위한 인터페이스 설정
 */
public interface ClassRoomService {
    boolean checkValidation(String authId, int classId);
    ClassRoomResponse createClassRoom(String authId, int classId);
    String createSession();
    ClassRoomResponse getToken(String sessionId, String teacherId);
    boolean closeSession(int classId, String authId);
}
