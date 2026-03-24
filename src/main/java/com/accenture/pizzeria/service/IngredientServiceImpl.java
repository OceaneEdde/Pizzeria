package com.accenture.pizzeria.service;

import com.accenture.pizzeria.exception.PizzeriaException;
import com.accenture.pizzeria.mapper.IngredientMapper;
import com.accenture.pizzeria.model.Ingredient;
import com.accenture.pizzeria.repository.IngredientRepository;
import com.accenture.pizzeria.service.dto.IngredientRequestDto;
import com.accenture.pizzeria.service.dto.IngredientResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class IngredientServiceImpl implements IngredientService{

    private IngredientRepository ingredientRepository;
    private IngredientMapper ingredientMapper;


    /**
     * Method to add a new Ingredient into the database
     * @param requestDto request send by user
     * @return response to confirm the save in the database
     * @throws PizzeriaException
     */
    @Override
    public IngredientResponseDto addIngredient(IngredientRequestDto requestDto) throws PizzeriaException {

        if (requestDto == null)
            throw new PizzeriaException("ingredient.dto.null", HttpStatus.BAD_REQUEST);

        Ingredient ingredient = ingredientMapper.toIngredient(requestDto);

        Ingredient saved = ingredientRepository.save(ingredient);

        return ingredientMapper.toIngredientResponseDto(saved);
    }
}
