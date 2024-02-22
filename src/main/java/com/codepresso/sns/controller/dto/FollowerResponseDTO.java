package com.codepresso.sns.controller.dto;

import com.codepresso.sns.vo.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FollowerResponseDTO {
    private Integer userId;
    private String userName;
    private String occupation;

    public static FollowerResponseDTO fromUser(User user) {
        FollowerResponseDTO dto = new FollowerResponseDTO();
        dto.setUserId(user.getUserId());
        dto.setUserName(user.getUserName());
        dto.setOccupation(user.getOccupation());
        return dto;
    }
}
