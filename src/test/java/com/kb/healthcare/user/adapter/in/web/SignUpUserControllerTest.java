package com.kb.healthcare.user.adapter.in.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kb.healthcare.configuration.AbstractWebMvcTestContext;
import com.kb.healthcare.user.application.port.in.command.SignUpUserCommand;
import com.kb.healthcare.user.application.service.SignUpUserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = SignUpUserController.class)
class SignUpUserControllerTest extends AbstractWebMvcTestContext {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private SignUpUserService signUpUserService;

    @Test
    @DisplayName(value = "회원가입한다")
    void signUp() throws Exception {
        // given
        SignUpUserRequest request = new SignUpUserRequest(
                "홍길동",
                "길동이",
                "gildong@example.com",
                "password1"
        );

        // when & then
        mockMvc.perform(
                        post("/api/v1/users")
                                .contentType(APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                )
                .andDo(print())
                .andExpect(status().isNoContent());

        verify(signUpUserService).signUp(any(SignUpUserCommand.class));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(
            strings = {
                    "김이름이너무길어요",
                    "James",
                    "Sam킴",
                    "존Park"
            }
    )
    @DisplayName(value = "잘못된 이름으로 회원가입할 수 없다.")
    void signUpWhenInvalidName(String name) throws Exception {
        // given
        SignUpUserRequest request = new SignUpUserRequest(
                name,
                "길동이",
                "gildong@example.com",
                "password1"
        );

        // when & then
        mockMvc.perform(
                        post("/api/v1/users")
                                .contentType(APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.message").isNotEmpty());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(
            strings = {
                    "가나다라마바사아자카파",
                    "abcdefghijk",
                    "12345678910"
            }
    )
    @DisplayName(value = "잘못된 닉네임으로 회원가입할 수 없다.")
    void signUpWhenInvalidNickName(String nickname) throws Exception {
        // given
        SignUpUserRequest request = new SignUpUserRequest(
                "홍길동",
                nickname,
                "gildong@example.com",
                "password1"
        );

        // when & then
        mockMvc.perform(
                        post("/api/v1/users")
                                .contentType(APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.message").isNotEmpty());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(
            strings = {
                    "가나다라마바사아자카파",
                    "abcdefghijk",
                    "12345678910",
                    "123@",
                    "123#gmail.com",
                    "123@123@"
            }
    )
    @DisplayName(value = "잘못된 이메일로 회원가입할 수 없다.")
    void signUpWhenInvalidEmail(String email) throws Exception {
        // given
        SignUpUserRequest request = new SignUpUserRequest(
                "홍길동",
                "길동이",
                email,
                "password1"
        );

        // when & then
        mockMvc.perform(
                        post("/api/v1/users")
                                .contentType(APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.message").isNotEmpty());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(
            strings = {
                    "가나다라마바사아자카파",
                    "abcdefghijk",
                    "12345678910"
            }
    )
    @DisplayName(value = "잘못된 비밀번호로 회원가입할 수 없다.")
    void signUpWhenInvalidPassword(String password) throws Exception {
        // given
        SignUpUserRequest request = new SignUpUserRequest(
                "홍길동",
                "길동이",
                "gildong@example.com",
                password
        );

        // when & then
        mockMvc.perform(
                        post("/api/v1/users")
                                .contentType(APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.message").isNotEmpty());
    }

}
