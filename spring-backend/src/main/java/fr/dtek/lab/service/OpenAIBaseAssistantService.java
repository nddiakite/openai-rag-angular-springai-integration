package fr.dtek.lab.service;

import fr.dtek.lab.conf.SocialConfig;
import fr.dtek.lab.jpa.MessageEntity;
import fr.dtek.lab.jpa.MessageEntityRepository;
import fr.dtek.lab.jpa.UserEntity;
import fr.dtek.lab.jpa.UserRepository;
import fr.dtek.lab.security.OpenAiThreadManager;
import com.theokanning.openai.completion.chat.ChatCompletionResult;
import com.theokanning.openai.completion.chat.ChatMessage;
import fr.dtek.lab.util.MessageUtil;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static java.util.UUID.randomUUID;

@RequiredArgsConstructor
@Slf4j
public class OpenAIBaseAssistantService {
    protected final SocialConfig socialConfig;
    protected final MessageEntityRepository messageEntityRepository;
    protected final UserRepository userRepository;
    protected final OpenAiThreadManager openAiThreadManager;

    protected static final Logger LOG = LoggerFactory.getLogger(OpenAIBaseAssistantService.class);

    public ChatCompletionResult getOpenAiResponse(String prompt, String username) {
        LOG.info("OpenAIBaseAssistantService.getOpenAiResponse => NO CALL has been planned for now !! ");

        return null;
    }

    public ChatMessage getOpenAiResponse(String prompt, String username, HttpSession session) {
        LOG.info("OpenAIBaseAssistantService.getOpenAiResponse => NO CALL has been planned for now !! ");

        return null;
    }

    public List<MessageEntity> getAllMessagesForUser(String username) {
        UserEntity user = userRepository.findByUsername(username).get();

        Iterable<MessageEntity> allByUserId = messageEntityRepository.findAllByUserId(user.getId());
        if (allByUserId == null || !allByUserId.iterator().hasNext()) {
            return saveInitialChatMessageForUser(user.getId());
        }
        return (List<MessageEntity>) allByUserId;
    }

    @Transactional
    public void deleteAllMessagesForUser(String username) {
        UserEntity user = userRepository.findByUsername(username).get();

        messageEntityRepository.deleteAllByUserId(user.getId());
    }

    protected List<ChatMessage> fetchChatMessages(UserEntity user) {
        Iterable<MessageEntity> allByUserId = messageEntityRepository.findAllByUserId(user.getId());
        if (allByUserId == null || !allByUserId.iterator().hasNext()) {
            log.info("No messages found for user: {}", user.getUsername());
            List<MessageEntity> messageEntities = saveInitialChatMessageForUser(user.getId());
            return MessageUtil.createChatMessagesFromEntities(messageEntities);
        }
        log.info("Found {} messages for user: {}", ((List<MessageEntity>) allByUserId).size(), user.getUsername());

        return MessageUtil.createChatMessagesFromEntities((List<MessageEntity>) allByUserId);
    }

    private List<MessageEntity> saveInitialChatMessageForUser(String userId) {
        List<ChatMessage> chatMessages = MessageUtil.createTemplateChatMessages();
        LocalDateTime now = LocalDateTime.now();
        List<MessageEntity> messageEntities = new ArrayList<>();

        for (int i = 0; i < chatMessages.size(); i++) {
            ChatMessage chatMessage = chatMessages.get(i);
            MessageEntity messageEntity = new MessageEntity();
            messageEntity.setId(randomUUID().toString());
            messageEntity.setUserId(userId);
            messageEntity.setMessageBy(MessageUtil.getMessageByFromRole(chatMessage.getRole()));
            messageEntity.setCreatedAt(now.plusNanos(i * 1000L));
            messageEntity.setMessageText(chatMessage.getContent());
            messageEntities.add(messageEntity);
        }
        messageEntityRepository.saveAll(messageEntities);
        return messageEntities;
    }

    protected void saveMessageEntity(ChatMessage chatMessage, String userId) {
        var entity = MessageUtil.createMessageEntityFromChatMessage(chatMessage, userId);
        messageEntityRepository.save(entity);
    }
}
