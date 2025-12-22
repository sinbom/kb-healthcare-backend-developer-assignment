package com.kb.healthcare.user.adapter.in.web;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Schema(title = "유저 회원가입 요청")
record SignUpUserRequest(
        @Schema(description = "유저 이름")
        @Size(
                max = 5,
                message = "이름은 {max}자 이내로 입력해주세요."
        )
        @Pattern(
                regexp = "^[가-힣]+$",
                message = "이름은 한글로 입력해주세요."
        )
        @NotBlank(message = "이름을 입력해주세요.")
        String name,

        @Schema(description = "유저 닉네임")
        @Size(
                max = 10,
                message = "닉네임을 {max}자 이내로 입력해주세요."
        )
        @NotBlank(message = "닉네임을 입력해주세요.")
        String nickname,

        @Schema(description = "유저 이메일")
        @Size(
                max = 320,
                message = "이메일을 {max}자 이내로 입력해주세요."
        )
        @Email(message = "이메일 형식이 잘못되었어요.")
        @NotBlank(message = "이메일을 입력해주세요.")
        String email,

        @Schema(description = "유저 비밀번호")
        @Size(
                max = 10,
                message = "비밀번호를 {max}자 이내로 입력해주세요."
        )
        @NotBlank(message = "비밀번호를 입력해주세요.")
        String password
) {
}
