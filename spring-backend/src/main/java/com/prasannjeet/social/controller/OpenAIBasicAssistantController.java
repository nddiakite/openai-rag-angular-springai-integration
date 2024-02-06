package com.prasannjeet.social.controller;

import com.prasannjeet.social.dto.ChatMessageDTO;
import com.prasannjeet.social.jpa.MessageEntity;
import com.prasannjeet.social.service.OpenAIBaseAssistantService;
import com.theokanning.openai.OpenAiHttpException;
import com.theokanning.openai.completion.chat.ChatCompletionResult;
import com.theokanning.openai.completion.chat.ChatMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.prasannjeet.social.util.MessageUtil.convertToChatMessageDTO;
import static java.util.Comparator.comparing;

@Slf4j
@RequiredArgsConstructor
public class OpenAIBasicAssistantController {
    protected final OpenAIBaseAssistantService openAIBaseAssistantService;
    protected final String assistantId;

    @CrossOrigin
    @GetMapping(value = "/allmessages", produces = "application/json")
    public List<ChatMessageDTO> getAllMessages() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        try {
            log.info("Sending all user messages for user {}", username);

            List<MessageEntity> allByUserId = openAIBaseAssistantService.getAllMessagesForUser(username);
            allByUserId.sort(comparing(MessageEntity::getCreatedAt));
            //Remove first 3 messages as they are the template messages
            allByUserId.remove(0);
//            allByUserId.remove(0);
//            allByUserId.remove(0);

            return convertToChatMessageDTO(allByUserId, this.assistantId);
        } catch (Exception e) {
            throw new RuntimeException("Couldn't get all messages for the user " + username, e);
        }
    }

    @CrossOrigin
    @DeleteMapping(value = "/clearChat")
    public void clearChat() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        try {
            log.info("Clearing all user messages for user {}", username);

            openAIBaseAssistantService.deleteAllMessagesForUser(username);
        } catch (Exception e) {
            log.error("Couldn't clear chat for the user " + username, e);
            throw new RuntimeException("Couldn't clear chat for the user " + username, e);
        }
    }
}
