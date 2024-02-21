package com.codepresso.sns.controller.dto;

import lombok.Getter;
import lombok.Setter;
import com.codepresso.sns.vo.User;

@Setter
@Getter
public class UpdateRequestDTO {
    String userName;
    String introduction;
    String occupation;
    String city;

    public User applyUpdates(User existingUser) {
        if (this.userName != null) {
            existingUser.setUserName(this.userName);
        }
        if (this.introduction != null) {
            existingUser.setIntroduction(this.introduction);
        }
        if (this.occupation != null) {
            existingUser.setOccupation(this.occupation);
        }
        if (this.city != null) {
            existingUser.setCity(this.city);
        }

        return existingUser;
    }

}