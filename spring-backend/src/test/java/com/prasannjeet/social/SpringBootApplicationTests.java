package com.prasannjeet.social;

import com.prasannjeet.social.security.PasswordEncoderUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.ai.autoconfigure.vectorstore.pgvector.PgVectorStoreAutoConfiguration;

@SpringBootTest
@ActiveProfiles("test")
@EnableAutoConfiguration(exclude={PgVectorStoreAutoConfiguration.class})
class SpringBootApplicationTests {

	@MockBean
	private JwtDecoder jwtDecoder;

	@Test
	void contextLoads() {
		PasswordEncoderUtil.main();
	}

}
