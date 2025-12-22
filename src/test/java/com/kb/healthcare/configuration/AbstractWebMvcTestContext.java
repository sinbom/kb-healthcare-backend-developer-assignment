package com.kb.healthcare.configuration;

import com.kb.healthcare.user.application.port.out.VerifyJwtPort;
import org.junit.jupiter.api.Disabled;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@Disabled
@WebMvcTest
@Import(value = SecurityConfiguration.class)
@ActiveProfiles(value = "test")
public abstract class AbstractWebMvcTestContext {

    @MockitoBean
    private VerifyJwtPort verifyJwtPort;

}
