package br.com.erudio.repository;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class DeepSeekRepository {
    private static final String API_URL = "https://api.deepseek.com/v1/chat/completions";
    private static final String API_KEY = System.getenv("DEEP_SEEK_API_KEY_ESTUDO_ERUDIO");

    public String doQuestionChat(String prompt) {

        String requestBody = getRequestBodyJson(prompt);

        var client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + API_KEY)
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        String resposneMessage;

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            resposneMessage = getContent(response);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao realizar requisição ao DeepSeek", e);
        }

        return resposneMessage;

    }

    public String doQuestionRecipe(String ingredients, String cuisine, String dietaryRestrictions) {

        var promptTemplate = String
                .format(" Eu desejo criar uma receita usando os seguintes infredientes:" +
                        " %s,O tipo de cozinha que eu prefiro é %s. Favor considerar as seguintes restrições alimentares: %s. " +
                        "Favor gere para mim o detalhe da receita contendo título, lista de infradientes e instruções para preparar e cozinhar",
                ingredients, cuisine, dietaryRestrictions
        );

        String requestBody = getRequestBodyJson(promptTemplate);

        var client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + API_KEY)
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        String resposneMessage;

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            resposneMessage = getContent(response);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao realizar requisição ao DeepSeek", e);
        }

        return resposneMessage;

    }

    private static String getRequestBodyJson(String promptTemplate) {
        String requestBody = String.format("""
                {
                    "model": "deepseek-chat",
                    "messages": [
                        {
                            "role": "user",
                            "content": "%s"
                        }
                    ],
                    "temperature": 0.7
                }
                """, promptTemplate);
        return requestBody;
    }

    private static String getContent(HttpResponse<String> response) {
        JSONObject jsonObject = new JSONObject(response.body());
        JSONArray choicesArray = jsonObject.getJSONArray("choices");
        JSONObject firstChoice = choicesArray.getJSONObject(0);
        JSONObject message = firstChoice.getJSONObject("message");
        String content = message.getString("content");
        return content;
    }


}
