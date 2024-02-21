package com.codepresso.sns.controller.dto;

import javax.validation.constraints.NotNull;

public record FollowRequestDTO(@NotNull Integer followerId, @NotNull Integer followingId) {
}
