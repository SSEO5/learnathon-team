package com.codepresso.sns.controller;

import com.codepresso.sns.controller.dto.*;
import com.codepresso.sns.controller.dto.*;
import com.codepresso.sns.service.UserService;
import com.codepresso.sns.vo.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.awt.image.PixelGrabber;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 회원가입
    @PostMapping("/user/signup")
    public ResponseEntity<SignUpResponseDTO> Signup(@Valid @RequestBody SignUpRequestDTO request) {
        if (userService.existsByEmail(request.email())){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already exists");
        }
        User newUser = userService.signup(request);
        SignUpResponseDTO responseDTO = new SignUpResponseDTO(newUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    // 로그인
    @PostMapping("/user/login")
    public ResponseEntity<LoginResponseDTO> Login(@Valid @RequestBody LoginRequestDTO request){
        if(userService.authenticateUser(request.email(), request.password())){
            User user = userService.getUserByEmail(request.email());
            return ResponseEntity.ok(new LoginResponseDTO(user));
        }
        else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid email or password");
        }
    }

    // 회원정보 간단 조회
    @GetMapping("/user/{userId}/summary")
    public UserInfoSummaryDTO getUserSummary(@PathVariable("userId") Integer userId) {
        User user = userService.getUserById(userId);
        if (user != null) {
            return new UserInfoSummaryDTO(user);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
    }

    // 회원정보 상세 조회
    @GetMapping("/user/{userId}/detail")
    public UserInfoDetailResponseDTO getUserDetail(@PathVariable("userId") Integer userId) {
        User user = userService.getUserById(userId);
        if (user != null) {
            return new UserInfoDetailResponseDTO(user);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
    }

    // 회원정보 수정
    @PatchMapping("/user/{userId}")
    public UserInfoDetailResponseDTO UpdateUserInfo(@PathVariable("userId") Integer userId, @RequestBody UpdateRequestDTO request) {
        User user = userService.getUserById(userId);
        if(user != null){
            User updatedUser = request.applyUpdates(user);
            userService.updateUserInfo(updatedUser);
            return new UserInfoDetailResponseDTO(updatedUser);
        }
        else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
    }

    // 비밀번호 수정
    @PatchMapping("/user/{userId}/password")
    public ResponseEntity UpdatePassword(@PathVariable("userId") Integer userId, @RequestBody PasswordRequestDTO request) {
        User user = userService.getUserById(userId);
        if(user == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        if(userService.authenticateUser(user.getEmail(), request.currentPassword())){
            user.setPassword(request.newPassword());
            userService.updatePassword(user);
            System.out.println(user.getPassword());

            Map<String, String> response = new HashMap<>();
            response.put("message", "Password successfully updated.");
            return ResponseEntity.ok().body(response);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid current password.");
        }
    }
}
