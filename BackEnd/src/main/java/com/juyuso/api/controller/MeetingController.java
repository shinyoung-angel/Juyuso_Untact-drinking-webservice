package com.juyuso.api.controller;

import com.juyuso.api.dto.request.MeetingCreateReqDto;
import com.juyuso.api.dto.response.MeetingCreateResDto;
import com.juyuso.api.service.MeetingService;
import com.juyuso.api.service.UserService;
import com.juyuso.db.entity.Meeting;
import com.juyuso.db.entity.User;
import io.openvidu.java.client.OpenVidu;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Api(value = "미팅방 관리 api")
@RestController
@RequestMapping("/api/meeting")
public class MeetingController {

    private final MeetingService meetingService;
    private final UserService userService;
    private OpenVidu openVidu;

    private Map<String, Integer> mapSessions = new ConcurrentHashMap<>();



    private String OPENVIDU_URL;
    private String SECRET;

    @Autowired
    public MeetingController(MeetingService meetingService, @Value("${openvidu.secret}") String secret, @Value("${openvidu.url}") String openviduUrl, UserService userService){
        this.meetingService = meetingService;
        this.userService = userService;
        this.SECRET = secret;
        this.OPENVIDU_URL = openviduUrl;
        this.openVidu = new OpenVidu(OPENVIDU_URL, SECRET);
    }


    @PostMapping("/create")
    @ApiOperation(value = "미팅방 만들기", notes = "<strong>방만들기</strong>")
    @ApiResponses({
            @ApiResponse(code = 200, message = "방만들기 성공 "),
            @ApiResponse(code = 400, message = "오류"),
            @ApiResponse(code = 401, message = "권한 없음"),
            @ApiResponse(code = 500, message = " 서버에러")
    })
//    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public MeetingCreateResDto createMeeting(@RequestBody MeetingCreateReqDto reqDto, Principal principal) {
        String userId = principal.getName();
        meetingService.createMeeting(reqDto, userId);
        return new MeetingCreateResDto(reqDto.getMeetingName(), reqDto.getMeetingPassword(), userId);
    }


}