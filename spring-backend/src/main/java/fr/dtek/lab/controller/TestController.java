package fr.dtek.lab.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
                new Document("Spring AI est su tonnerre car c'est une extraordinaire plateforme pour construire des applications, j'adore !!",
                        Map.of("springai", "openai")),
                new Document("Le monde d'aujourd'hui doit être un combat salvateur et projetons nous car la solution est peut être dans un proche avenir."),
                new Document("Avec le temps, on avance à l'envers en faisant face au passé et dos à l'avenir.",
                        Map.of("philosophie", "proverbe naby")));
//        List<Document> documents = List.of(
//                new Document("Spring AI rocks because it's a cray great platform to build on AI solutions, I love !!",
//                        Map.of("meta1", "meta1")),
//                new Document("The world is big and salvation lurks around the corner"),
//                new Document("You walk forward facing the past and you turn back toward the future.",
//                        Map.of("meta2", "meta2")));

        vectorStore.add(documents);

        List<Document> results = vectorStore.similaritySearch(SearchRequest.query(prompt).withTopK(5));

        String responseApiTest = "<div><strong>YEAH => ALL IS WORKING FINE !! </strong></div></br>";
        responseApiTest = responseApiTest.concat("<div><strong>QUESTION :</strong> " + prompt + "</div></br>");
        responseApiTest = responseApiTest.concat("<div><strong>REPONSE :</strong> " + results.get(0).getContent() + "</div></br>");

        return responseApiTest;
    }
}
