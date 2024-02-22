package com.codepresso.sns.service;

import com.codepresso.sns.controller.dto.SignUpRequestDTO;
import com.codepresso.sns.mapper.UserMapper;
import com.codepresso.sns.vo.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Service
public class UserService {

    private final UserMapper userMapper;
    public UserService(UserMapper userMapper){
        this.userMapper = userMapper;
    }
//    private final BCryptPasswordEncoder passwordEncoder;

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
//        String hashedPassword = passwordEncoder.encode(request.getPassword());

        User newUser = new User();
        newUser.setUserName(request.getUserName());
        newUser.setEmail(request.getEmail());
        newUser.setPassword(request.getPassword());
        //newUser.setPassword(hashedPassword);
        newUser.setIntroduction(request.getIntroduction());
        newUser.setOccupation(request.getOccupation());
        newUser.setBirthday(request.getBirthday());
        newUser.setCity(request.getCity());
        userMapper.save(newUser);
        return newUser;
    }

    public boolean existsByEmail(String email){
        return userMapper.existsByEmail(email)==1;
    }

}
