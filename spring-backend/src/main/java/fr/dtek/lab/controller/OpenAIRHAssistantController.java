package fr.dtek.lab.controller;

import fr.dtek.lab.service.OpenAIRHAssistantService;
import com.theokanning.openai.OpenAiHttpException;
import com.theokanning.openai.completion.chat.ChatMessage;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/assistants/rh_assistant")
@Slf4j
//@PreAuthorize("hasRole('openapi')")
public class OpenAIRHAssistantController extends OpenAIBasicAssistantController {

    public OpenAIRHAssistantController(OpenAIRHAssistantService openAIRHAssistantService, String assistantId) {
        super(openAIRHAssistantService, assistantId);
    }

    @CrossOrigin
    @PostMapping(value = "/completion")
    public ChatMessage getCompletion(@RequestParam("prompt") String prompt, HttpSession session) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        try {
            log.info("Received request for prompt: {} and user: {}", prompt, username);
            ChatMessage message = this.openAIBaseAssistantService.getOpenAiResponse(prompt, username, session);
            log.info("Returning completion: {}", message);
            return message;
        } catch (Exception e) {
            if (e instanceof OpenAiHttpException) {
                log.error("Error from OpenAI: {}", e.getMessage());
            }
            throw new RuntimeException("Some problems when getting completion from OpenAI", e);
        }
    }
}
