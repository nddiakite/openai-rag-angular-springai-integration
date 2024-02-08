package com.prasannjeet.social.controller;

import com.prasannjeet.social.jpa.UserEntity;
import com.prasannjeet.social.jpa.UserRepository;
import com.prasannjeet.social.response.UserRest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/tests")
public class TestController {
    @Autowired
    private VectorStore vectorStore;

    private static final Logger LOG = LoggerFactory.getLogger(TestController.class);

    @CrossOrigin
    @GetMapping("/pg_vector_storage")
    public String apiTesting(@RequestParam(value = "prompt",
            defaultValue = "What do you think about Spring ?") String prompt) {
        LOG.info("TESTING API for NDI !!");

        // URL TEST : http://localhost:8082/api/tests/pg_vector_storage?prompt=%22XX_XX%22
        // REMPLACER XX_XX par une question sur les données ci-dessous
        List<Document> documents = List.of(
                new Document("Spring AI rocks because it's a cray great platform to build on AI solutions, I love !!",
                        Map.of("meta1", "meta1")),
                new Document("The world is big and salvation lurks around the corner"),
                new Document("You walk forward facing the past and you turn back toward the future.",
                        Map.of("meta2", "meta2")));

        vectorStore.add(documents);

        // Just for testing purpose, should be deleted later !!
//        waitForThisLong(2000);

//        List<Document> results = vectorStore.similaritySearch("Spring");
        List<Document> results = vectorStore.similaritySearch(SearchRequest.query(prompt).withTopK(5));

//        for (Document document : results) {
//            LOG.info("DOCUMENT (Order = " + document.getId() + ") : " + document.getContent());
//        }

        String responseApiTest = "All is working fine !!";
        responseApiTest = responseApiTest.concat("\n\nQUESTION : " + prompt);
        responseApiTest = responseApiTest.concat("\n\nRESPONSE : " + results.get(0).getContent());

        return responseApiTest;
    }
}
