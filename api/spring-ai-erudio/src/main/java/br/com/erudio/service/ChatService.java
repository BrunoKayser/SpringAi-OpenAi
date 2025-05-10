package br.com.erudio.service;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.stereotype.Service;

@Service
public class ChatService {

    private final ChatModel chatModel;

    public ChatService(ChatModel chatModel) {
        this.chatModel = chatModel;
    }

    /**
     * Endpoint para obter resposta do chat através de um texto
     * @param prompt serve para obter
     * @return resposta gerada pela chatGPT
     */
    public String getResponse(String prompt) {
        return chatModel.call(prompt);
    }

    /**
     * Endpoint para obter resposta do chat através de um texto, com configuração de resposta pelo modelo gpt-40 e com resposta mais sensata pela configuração de temperatura
     * @param prompt serve para obter
     * @return resposta gerada pela chatGPT
     */
    public String getResponseWithOptions(String prompt, double temperature) {
        ChatResponse response = chatModel.call(
                new Prompt(
                        prompt,
                        OpenAiChatOptions.builder() // Todos os parâmetros que podem ser utilizados: https://platform.openai.com/docs/api-reference/chat/create
                                .model("gpt-4o")  // Qual modelo de IA da OpenAPI usar: https://platform.openai.com/docs/models
                                .temperature(temperature) // Configura o chat quanto a sua resposta, quanto maior o valor, mais "maluca/criativo" é a resposta, quanto menor, mais sensato a resposta é.
                                .build()
                ));

        return response.getResult().getOutput().getText();
    }

}
