package fr.dtek.lab.service;

import fr.dtek.lab.conf.SocialConfig;
import fr.dtek.lab.jpa.MessageEntityRepository;
import fr.dtek.lab.jpa.UserEntity;
import fr.dtek.lab.jpa.UserRepository;
import fr.dtek.lab.security.OpenAiThreadManager;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatCompletionResult;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.OpenAiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class OpenAIClassicAssistantService extends OpenAIBaseAssistantService {
    public OpenAIClassicAssistantService(SocialConfig socialConfig, MessageEntityRepository messageEntityRepository,
                                         UserRepository userRepository, OpenAiThreadManager openAiThreadManager) {
        super(socialConfig, messageEntityRepository, userRepository, openAiThreadManager);
    }

    @Override
    public ChatCompletionResult getOpenAiResponse(String prompt, String username) {
        UserEntity user = userRepository.findByUsername(username).get();

        var service = new OpenAiService(socialConfig.getOpenAiToken());
        List<ChatMessage> chatMessages = fetchChatMessages(user);

        ChatMessage singletonMessage = new ChatMessage("user", prompt);
        chatMessages.add(singletonMessage);

        var request = getCompletionRequest(chatMessages, user.getId());
        var chatCompletion = service.createChatCompletion(request);
        var message = chatCompletion.getChoices().get(0).getMessage();

        saveMessageEntity(singletonMessage, user.getId());
        saveMessageEntity(message, user.getId());
        return chatCompletion;
    }

    private ChatCompletionRequest getCompletionRequest(List<ChatMessage> messages, String userId) {
        return ChatCompletionRequest.builder()
                .model(socialConfig.getOpenAiClassicAssistantModel())
                .messages(messages)
                .temperature(0.4)
                .n(1)
                .stream(false)
                .maxTokens(1000)
                .user(userId)
                .build();
    }
}
