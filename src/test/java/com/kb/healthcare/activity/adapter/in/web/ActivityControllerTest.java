package com.kb.healthcare.activity.adapter.in.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kb.healthcare.activity.adapter.in.web.SaveActivitiesRequest.Activities;
import com.kb.healthcare.activity.adapter.in.web.SaveActivitiesRequest.Activities.Calorie;
import com.kb.healthcare.activity.adapter.in.web.SaveActivitiesRequest.Activities.Distance;
import com.kb.healthcare.activity.adapter.in.web.SaveActivitiesRequest.Activities.Period;
import com.kb.healthcare.activity.adapter.in.web.SaveActivitiesRequest.Source.Product;
import com.kb.healthcare.activity.application.service.SaveActivityService;
import com.kb.healthcare.configuration.AbstractWebMvcTestContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static com.kb.healthcare.activity.domain.ActivityType.STEPS;
import static com.kb.healthcare.activity.domain.CalorieUnit.KCAL;
import static com.kb.healthcare.activity.domain.DistanceUnit.KM;
import static java.time.LocalDateTime.now;
import static java.util.UUID.randomUUID;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.verify;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ActivityController.class)
class ActivityControllerTest extends AbstractWebMvcTestContext {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private SaveActivityService saveActivityService;

    @Test
    @WithMockUser
    @DisplayName(value = "활동을 저장한다.")
    void save() throws Exception {
        // given
        SaveActivitiesRequest request = create();

        // when & then
        mockMvc.perform(
                        post("/api/v1/activities")
                                .contentType(APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                )
                .andDo(print())
                .andExpect(status().isNoContent());

        verify(saveActivityService).save(anyList());
    }

    @Test
    @DisplayName(value = "로그인 유저만 활동을 저장할 수 있다.")
    void saveWhenUnAuthorized() throws Exception {
        // given
        SaveActivitiesRequest request = create();

        // when & then
        mockMvc.perform(
                        post("/api/v1/activities")
                                .contentType(APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                )
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    private SaveActivitiesRequest create() {
        return new SaveActivitiesRequest(
                randomUUID()
                        .toString(),
                new Activities(
                        "evening walk",
                        List.of(
                                new Activities.Activity(
                                        new Period(
                                                now(),
                                                now().plusMinutes(10)
                                        ),
                                        new Distance(
                                                KM,
                                                new BigDecimal("1.2345678901234567890123456789")
                                        ),
                                        new Calorie(
                                                KCAL,
                                                new BigDecimal("75.5678901234567890123456789")
                                        ),
                                        new BigDecimal("1000")
                                )
                        ),
                        new SaveActivitiesRequest.Source(
                                new Product(
                                        "apple",
                                        "watch"
                                ),
                                "1",
                                "health",
                                "app"
                        )
                ),
                now(),
                STEPS
        );
    }

}
