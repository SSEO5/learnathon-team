package com.codepresso.sns.controller.dto;

import com.codepresso.sns.vo.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FollowingResponseDTO {
    private Integer userId;
    private String userName;
    private String occupation;

    public static FollowingResponseDTO fromUser(User user) {
        FollowingResponseDTO dto = new FollowingResponseDTO();
        dto.setUserId(user.getUserId());
        dto.setUserName(user.getUserName());
        dto.setOccupation(user.getOccupation());
        return dto;
    }
}
