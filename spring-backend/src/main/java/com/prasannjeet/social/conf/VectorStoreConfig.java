package com.prasannjeet.social.conf;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.ai.embedding.EmbeddingClient;
import org.springframework.ai.vectorstore.PgVectorStore;
import org.springframework.ai.vectorstore.VectorStore;

@Data
@Configuration
//@Profile("!test") // Cette configuration ne sera pas chargée en profil test
@EnableConfigurationProperties
@ConfigurationProperties("vector-store-config")
public class VectorStoreConfig {

    private static final Logger LOG = LoggerFactory.getLogger(VectorStoreConfig.class);

    private String apiKey;

    @Bean
    public VectorStore vectorStore(JdbcTemplate jdbcTemplate, EmbeddingClient embeddingClient) {
        return new PgVectorStore(jdbcTemplate, embeddingClient);
    }

}
