package com.kb.healthcare.configuration;

import org.junit.jupiter.api.Disabled;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@Disabled
@DataJpaTest
@Import(value = JpaConfiguration.class)
@ActiveProfiles(value = "test")
public abstract class AbstractDataJpaTestContext {

}
