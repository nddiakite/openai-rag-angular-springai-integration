package fr.dtek.lab.service;

import fr.dtek.lab.conf.SocialConfig;
import fr.dtek.lab.jpa.MessageEntityRepository;
import fr.dtek.lab.jpa.UserEntity;
import fr.dtek.lab.jpa.UserRepository;
import fr.dtek.lab.security.OpenAiThreadManager;
import com.theokanning.openai.OpenAiResponse;
import com.theokanning.openai.assistants.Assistant;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.messages.Message;
import com.theokanning.openai.messages.MessageRequest;
import com.theokanning.openai.runs.Run;
import com.theokanning.openai.runs.RunCreateRequest;
import com.theokanning.openai.service.OpenAiService;
import com.theokanning.openai.threads.Thread;
import com.theokanning.openai.threads.ThreadRequest;
import fr.dtek.lab.util.MessageUtil;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@Service
@Slf4j
public class OpenAIRHAssistantService extends OpenAIBaseAssistantService {
    public OpenAIRHAssistantService(SocialConfig socialConfig, MessageEntityRepository messageEntityRepository,
                                    UserRepository userRepository, OpenAiThreadManager openAiThreadManager) {
        super(socialConfig, messageEntityRepository, userRepository, openAiThreadManager);
    }

    // TODO-NDI : API calls too long with assistant, maybe display some messages on client side when waiting aPI's response
    @Override
    public ChatMessage getOpenAiResponse(String prompt, String username, HttpSession session) {
        UserEntity user = userRepository.findByUsername(username).get();

        var service = new OpenAiService(socialConfig.getOpenAiToken());
        List<ChatMessage> chatMessages = fetchChatMessages(user);

        ChatMessage singletonMessage = new ChatMessage("user", prompt);
        chatMessages.add(singletonMessage);

        // Récupérer l'assistant
        Assistant assistant = service.retrieveAssistant(socialConfig.getOpenAiRHAssistantId());

        // Récupérer le Thread utilisateur si déjà ouvert
        Thread userThread = openAiThreadManager.getOrSave(session, null);
        if (userThread == null) {
            userThread = service.createThread(
                    ThreadRequest.builder()
                            .messages(
                                    Stream.of(
                                            new MessageRequest("user", prompt, null, null)
                                    ).collect(ArrayList::new, ArrayList::add, ArrayList::addAll)
                            )
                            .build()
            );

            openAiThreadManager.getOrSave(session, userThread);
        }

        LOG.info("API CALL THREAD ID (Username : " + username + ") => " + userThread.getId());

        // Lancer la requête
        Run userRun = service.createRun(
                userThread.getId(),
                RunCreateRequest.builder()
                        .assistantId(assistant.getId())
                        // NOTE : this will override the initial instruction provided in the assistant default configuration
                        // IF a need to more instructions, append the new ones to the default instructions instead
//                        .instructions("Le login de l'utilisateur est :  " + username)
                        .build()
                );

        // Attendre que le status est passe à 'completed' avant de lire le message
        String openaiAPICallStatus;
        int openaiAPICallWaitingCount = 0;
        int openAiAPIWaitingTimeInMilliSeconds = Integer.parseInt(socialConfig.getOpenAiAPIWaitingTimeInMilliSeconds());
        int openAiAPIWaitingNbResponseRetrieval = Integer.parseInt(socialConfig.getOpenAiAPIWaitingNbResponseRetrieval());

        LOG.info("OPENAI API CALL : Starting ..............");
        LOG.info("User Prompt : " + prompt);

        do {
            // Lire la réponse
            Run userRetrieveRun = service.retrieveRun(userThread.getId(), userRun.getId());

            openaiAPICallStatus = userRetrieveRun.getStatus();

            // In case of failure
            if (openaiAPICallStatus.equals("completed")) {
                LOG.info("OPENAI API CALL SUCCESS ..............");
            }
            else if ( (!Arrays.asList("queued", "completed").contains(openaiAPICallStatus)) &&
                    (openaiAPICallWaitingCount > openAiAPIWaitingNbResponseRetrieval)) {
                LOG.info("OPENAI API CALL : ERROR OCCURED ON STATUS OR TIMEOUT ..............");
                LOG.info("API CALL STATUS : " + openaiAPICallStatus);
                LOG.info("User Prompt : " + prompt);

                break;
            }

            LOG.info("API CALL WAITING COUNT : " + openaiAPICallWaitingCount);

            MessageUtil.waitForThisLong(openAiAPIWaitingTimeInMilliSeconds);

            openaiAPICallWaitingCount++;
        }
        while(!openaiAPICallStatus.equals("completed"));

        // Construire la réponse
        ChatMessage responseMessage = new ChatMessage("system", "AUCUNE Reponse => Comportement Etrange, voir avec ADMIN !?");

        if (openaiAPICallStatus.equals("completed")) {
            OpenAiResponse<Message> responseMessages = service.listMessages(userThread.getId());

            saveMessageEntity(singletonMessage, user.getId());
            responseMessage = new ChatMessage("system", responseMessages.getData().get(0).getContent().get(0).getText().getValue());
            saveMessageEntity(responseMessage, user.getId());
        } else {
            responseMessage = new ChatMessage("system", "ECHEC Reponse => Voici le statut : " + openaiAPICallStatus);
        }

        return responseMessage;
    }

    // TODO-NDI : Later remove the strange suffix
    // Useful link : https://community.openai.com/t/what-are-n-source-in-openais-assistant-api-response/513857/9
    /*
        import re

        string = "Sample【25†source】"
        regex_pattern = r"【.*?】"
        cleaned_string = re.sub(regex_pattern, '', string)
        # Result is "Sample"
     */
}
