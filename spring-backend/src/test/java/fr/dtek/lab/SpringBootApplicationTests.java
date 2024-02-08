package fr.dtek.lab;

import fr.dtek.lab.security.PasswordEncoderUtil;
import org.junit.jupiter.api.Test;
import org.springframework.ai.autoconfigure.vectorstore.pgvector.PgVectorStoreAutoConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
@EnableAutoConfiguration(exclude={PgVectorStoreAutoConfiguration.class})
class SpringBootApplicationTests {

	@Test
	void contextLoads() {
		PasswordEncoderUtil.main();
	}
}
