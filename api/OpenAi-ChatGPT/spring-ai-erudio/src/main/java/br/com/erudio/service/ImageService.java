package br.com.erudio.service;

import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.image.ImageResponse;
import org.springframework.ai.openai.OpenAiImageModel;
import org.springframework.ai.openai.OpenAiImageOptions;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImageService {

    private final OpenAiImageModel openaiImageModel;

    public ImageService(OpenAiImageModel openaiImageModel) {
        this.openaiImageModel = openaiImageModel;
    }

    /**
     *
     * Endpoint para gerar uma imagem através da OpenAi e parâmetros fornecidos pelo usuário
     *
     * @param prompt: Contexto da imagem a ser gerada
     * @param quality: Qualidade da imagem
     * @param quantityImages: Quantidade de imagens geradas
     * @param height: Altura da imagem
     * @param width: Largura da imagem
     * @return Retorna uma lista de URLS com o endereço das imagens geradas pela OpenAi
     */
    public List<String> generateImage(String prompt, String quality, int quantityImages, int height, int width) {
        ImageResponse response = openaiImageModel.call( //Doc do spring: https://docs.spring.io/spring-ai/reference/api/image/openai-image.html
                new ImagePrompt(prompt,
                        OpenAiImageOptions.builder()
                                //.model("gpt-image-1") Tentar depois essas configus com o gpt image
                                //.quality("low")
                                //.N(4)
                                .quality(quality)
                                .N(quantityImages)
                                .height(height)
                                .width(width).build()));

        return response
                .getResults()
                .stream()
                .map(result -> result.getOutput().getUrl())
                .toList();

    }

}
