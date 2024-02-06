package com.prasannjeet.social.controller;

import com.prasannjeet.social.service.OpenAIClassicAssistantService;
import com.theokanning.openai.OpenAiHttpException;
import com.theokanning.openai.completion.chat.ChatCompletionResult;
import com.theokanning.openai.completion.chat.ChatMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/assistants/classic_assistant")
@Slf4j
//@PreAuthorize("hasRole('openapi')")
public class OpenAIClassicAssistantController extends OpenAIBasicAssistantController {

    public OpenAIClassicAssistantController(OpenAIClassicAssistantService openAIClassicAssistantService, String assistantId) {
        super(openAIClassicAssistantService, assistantId);
    }

    @CrossOrigin
    @PostMapping(value = "/completion")
    public ChatMessage getCompletion(@RequestParam("prompt") String prompt) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        try {
            log.info("Received request for prompt: {} and user: {}", prompt, username);
            ChatCompletionResult openAiResponse = openAIBaseAssistantService.getOpenAiResponse(prompt, username);
            ChatMessage message = openAiResponse.getChoices().get(0).getMessage();
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