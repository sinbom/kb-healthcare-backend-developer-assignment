package com.kb.healthcare.user.adapter.in.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kb.healthcare.configuration.AbstractWebMvcTestContext;
import com.kb.healthcare.user.adapter.in.web.request.LoginUserRequest;
import com.kb.healthcare.user.application.port.in.LoginUserUseCase;
import com.kb.healthcare.user.application.port.in.command.LoginUserCommand;
import com.kb.healthcare.user.domain.UserToken;
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
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = LoginUserController.class)
class LoginUserControllerTest extends AbstractWebMvcTestContext {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private LoginUserUseCase loginUserUseCase;

    @Test
    @DisplayName(value = "로그인한다.")
    void login() throws Exception {
        // given
        LoginUserRequest request = new LoginUserRequest(
                "gildong@example.com",
                "password1"
        );

        when(loginUserUseCase.login(any(LoginUserCommand.class)))
                .thenReturn(UserToken.builder().accessToken("jwt.token").build());

        // when & then
        mockMvc.perform(
                        post("/api/v1/users/login")
                                .contentType(APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.accessToken").isNotEmpty());

        verify(loginUserUseCase).login(any(LoginUserCommand.class));
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
    @DisplayName(value = "잘못된 이메일로 로그인인할 수 없다.")
    void loginWhenInvalidEmail(String email) throws Exception {
        // given
        LoginUserRequest request = new LoginUserRequest(
                email,
                "password1"
        );

        // when & then
        mockMvc.perform(
                        post("/api/v1/users/login")
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
    @DisplayName(value = "잘못된 비밀번호로 로그인할 수 없다.")
    void loginWhenInvalidPassword(String password) throws Exception {
        // given
        LoginUserRequest request = new LoginUserRequest(
                "gildong@example.com",
                password
        );

        // when & then
        mockMvc.perform(
                        post("/api/v1/users/login")
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
