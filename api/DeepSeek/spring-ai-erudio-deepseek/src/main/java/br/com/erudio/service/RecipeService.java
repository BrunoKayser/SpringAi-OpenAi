package br.com.erudio.service;

import br.com.erudio.repository.DeepSeekRepository;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class RecipeService {

    private final ChatModel chatModel;
    private final DeepSeekRepository deepSeekRepository;

    public RecipeService(ChatModel chatModel, DeepSeekRepository deepSeekRepository) {
        this.chatModel = chatModel;
        this.deepSeekRepository = deepSeekRepository;
    }

    /**
     *
     * Service para retornar receitas geradas pelo chatGPT através de um template preparado para receber as informações de
     * infredientes, cozinha e restrição alimentar informadas pelo usuário.
     *
     * @param ingredients: Ingrediente da receita
     * @param cuisine: Tipo de cozinha para gerar a receita
     * @param dietaryRestrictions: Restrição alimentar
     * @return Receita gerada pelo chatGPT
     */

    public String createRecipe(String ingredients, String cuisine, String dietaryRestrictions) {

    /* Comentada a integração com SpringIA + deepseek por que não esta funcionando no spring, acho que eles ainda  não estão com ela pronta

    var template = """
               Eu desejo criar uma receita usando os seguintes infredientes: {ingredients}
               O tipo de cozinha que eu prefiro é {cuisine}.
               Favor considerar as seguintes restrições alimentares: {dietaryRestrictions}.
               Favor gere para mim o detalhe da receita contendo título, lista de infradientes e instruções para preparar e cozinhar
               """;

        var promptTemplate = new PromptTemplate(template);

        Map<String, Object> params = Map.of(
                "ingredients", ingredients,
                "cuisine", cuisine,
                "dietaryRestrictions", dietaryRestrictions
        );

        var prompt = promptTemplate.create(params);

        return chatModel.call(prompt).getResult().getOutput().getText();

     */

        return deepSeekRepository.doQuestionRecipe(ingredients, cuisine, dietaryRestrictions);

    }
}
