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
import java.util.List;

@Controller
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 회원가입
    @PostMapping("/user/signup")
    public ResponseEntity<SignUpResponseDTO> Signup(@Valid @RequestBody SignUpRequestDTO request) {
        // 필수 필드가 누락
        if (request.getUserName() == null || request.getEmail() == null || request.getPassword() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Required fields are missing");
        }
        if (userService.existsByEmail(request.getEmail())){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already exists");
        }
        User newUser = userService.signup(request);
        SignUpResponseDTO responseDTO = new SignUpResponseDTO(newUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    // 로그인
    @PostMapping("/user/login")
    public LoginResponseDTO Login(@Valid @RequestBody LoginRequestDTO request){
        User user = userService.getUserByEmail(request.email());
        if(user != null && user.getPassword().equals(request.password())){
            return new LoginResponseDTO(user);
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
    public String UpdatePassword(@PathVariable("userId") Integer userId, @RequestBody PasswordRequestDTO request) {
        User user = userService.getUserById(userId);
        if (user != null) {
            String currentPassword = user.getPassword();
            String newPassword = request.newPassword();
            System.out.println(currentPassword);
            if (currentPassword.equals(request.currentPassword())) {
                user.setPassword(newPassword);
                userService.updatePassword(user);
                return "Password successfully updated.";
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid current password.");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
    }

}
