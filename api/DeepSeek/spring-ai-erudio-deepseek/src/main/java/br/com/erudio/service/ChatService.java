package br.com.erudio.service;

import br.com.erudio.repository.DeepSeekRepository;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.deepseek.DeepSeekChatOptions;
import org.springframework.ai.deepseek.api.DeepSeekApi;
import org.springframework.stereotype.Service;

@Service
public class ChatService {

    private final ChatModel chatModel;
    private final DeepSeekRepository deepSeekRepository;

    public ChatService(ChatModel chatModel, DeepSeekRepository deepSeekRepository) {
        this.chatModel = chatModel;
        this.deepSeekRepository = deepSeekRepository;
    }

    /**
     * Endpoint para obter resposta do chat através de um texto
     * @param prompt serve para obter
     * @return resposta gerada pela chatGPT
     */
    public String getResponse(String prompt) {
        //Comentada a integração com SpringIA + deepseek por que não esta funcionando no spring, acho que eles ainda  não estão com ela pronta
        //return chatModel.call(prompt);
        return deepSeekRepository.doQuestionChat(prompt);
    }

    /**
     * Endpoint para obter resposta do chat através de um texto, com configuração de resposta pelo modelo gpt-40 e com resposta mais sensata pela configuração de temperatura
     * @param prompt serve para obter
     * @return resposta gerada pela chatGPT
     */
    public String getResponseWithOptions(String prompt, double temperature) {
     /*  Comentada a integração com SpringIA + deepseek por que não esta funcionando no spring, acho que eles ainda  não estão com ela pronta
      ChatResponse response = chatModel.call(
                new Prompt(
                        "Generate the names of 5 famous pirates. Please provide the JSON response without any code block markers such as ```json```.",
                        DeepSeekChatOptions.builder()
                                .model(DeepSeekApi.ChatModel.DEEPSEEK_CHAT.getValue())
                                .temperature(0.8)
                                .build()
                ));

        return response.getResult().getOutput().getText();
      */

        return deepSeekRepository.doQuestionChat(prompt);
    }

}
