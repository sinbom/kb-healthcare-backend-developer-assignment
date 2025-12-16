package com.kb.healthcare.user.adapter.in.web;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

record SignUpUserRequest(
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

        @Size(
                max = 10,
                message = "닉네임을 {max}자 이내로 입력해주세요."
        )
        @NotBlank(message = "닉네임을 입력해주세요.")
        String nickname,

        @Size(
                max = 320,
                message = "이메일을 {max}자 이내로 입력해주세요."
        )
        @Email(message = "이메일 형식이 잘못되었어요.")
        @NotBlank(message = "이메일을 입력해주세요.")
        String email,

        @Size(
                max = 10,
                message = "비밀번호를 {max}자 이내로 입력해주세요."
        )
        @NotBlank(message = "비밀번호를 입력해주세요.")
        String password
) {
}
