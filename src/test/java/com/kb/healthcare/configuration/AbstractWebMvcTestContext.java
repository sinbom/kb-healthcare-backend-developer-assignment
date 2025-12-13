package com.kb.healthcare.configuration;

import org.junit.jupiter.api.Disabled;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@Disabled
@WebMvcTest
@Import(value = SecurityConfiguration.class)
@ActiveProfiles(value = "test")
public abstract class AbstractWebMvcTestContext {

}
