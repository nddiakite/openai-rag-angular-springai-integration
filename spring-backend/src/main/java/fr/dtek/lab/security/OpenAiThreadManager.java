package fr.dtek.lab.security;

import com.theokanning.openai.threads.Thread;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

@Service
public class OpenAiThreadManager {
    // TODO-NDI : Fix the issue that creates new session each API call, because, it's make useless the thread manager currently
    // TODO-NDI : Even later, think of using Cache like Redis for scalability (in case of crashes of user sessions, etc.)
    public Thread getOrSave(HttpSession session, Thread userThread) {
        OpenaiAPIThreadContext openaiAPIThreadContext = (OpenaiAPIThreadContext) session.getAttribute("OPENAI_API_USER_THREAD");
        Thread thread = null;

        if (userThread == null && openaiAPIThreadContext != null) {
            thread = openaiAPIThreadContext.thread;
        } else {
            session.setAttribute("OPENAI_API_USER_THREAD", new OpenaiAPIThreadContext(userThread));
        }

        return thread;
    }

    static class OpenaiAPIThreadContext {
        Thread thread;

        public OpenaiAPIThreadContext(Thread thread) {
            this.thread = thread;
        }
    }
}
