package com.codepresso.sns.service;

import com.codepresso.sns.controller.dto.SignUpRequestDTO;
import com.codepresso.sns.mapper.UserMapper;
import com.codepresso.sns.vo.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;

    @Autowired
    private final BCryptPasswordEncoder passwordEncoder;

    public User getUserById(Integer userId) {
        return userMapper.getUserById(userId);
    }

    public User getUserByEmail(String email) {
        return userMapper.getUserByEmail(email);
    }

    public void updateUserInfo(User user){
        userMapper.updateUserInfo(user);
    }

    public void updatePassword(User user){
        userMapper.updatePassword(user);
    }

    public User signup(SignUpRequestDTO request) {
        String hashedPassword = passwordEncoder.encode(request.password());

        User newUser = new User();
        newUser.setUserName(request.userName());
        newUser.setEmail(request.email());
        //newUser.setPassword(request.getPassword());
        newUser.setPassword(hashedPassword);
        newUser.setIntroduction(request.introduction());
        newUser.setOccupation(request.occupation());
        newUser.setBirthday(request.birthday());
        newUser.setCity(request.city());
        userMapper.save(newUser);
        return newUser;
    }

    public boolean existsByEmail(String email){
        return userMapper.existsByEmail(email)==1;
    }


    public boolean authenticateUser(String email, String password) {
        User user = getUserByEmail(email);
        // 사용자가 존재하고, 입력한 비밀번호가 암호화된 비밀번호와 일치하는지 검증
        return user != null && passwordEncoder.matches(password, user.getPassword());
    }

}
