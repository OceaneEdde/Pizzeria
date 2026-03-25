package com.accenture.pizzeria.service.impl;

import com.accenture.pizzeria.exception.PizzeriaException;
import com.accenture.pizzeria.mapper.IngredientMapper;
import com.accenture.pizzeria.model.Ingredient;
import com.accenture.pizzeria.repository.IngredientRepository;
import com.accenture.pizzeria.service.IngredientService;
import com.accenture.pizzeria.service.dto.IngredientRequestDto;
import com.accenture.pizzeria.service.dto.IngredientResponseDto;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@Service
public class IngredientServiceImpl implements IngredientService {

    private IngredientRepository ingredientRepository;
    private IngredientMapper ingredientMapper;


    /**
     * Method to add a new Ingredient into the database
     *
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

    /**
     * Method to find all the Ingredients in the database
     *
     * @return List of IngredientResponseDto reflecting the Ingredients in the database
     */
    @Override
    public List<IngredientResponseDto> findAll() {
        return ingredientRepository.findAll().stream().map(ingredient -> ingredientMapper.toIngredientResponseDto(ingredient)).toList();
    }

    /**
     * Method to find an Ingredient by it's Id in the database.
     * @param id the Id on which we query the repository.
     * @return An IngredientResponseDto reflecting the Ingredient found in the database.
     * @throws PizzeriaException when the Id is null.
     */
    @Override
    public IngredientResponseDto findById(UUID id) throws PizzeriaException {
        if (null == id)
            throw new PizzeriaException("id.null", HttpStatus.BAD_REQUEST);
        Optional<Ingredient> optIngredient = ingredientRepository.findById(id);
        if (optIngredient.isEmpty())
            throw new EntityNotFoundException("ingredient.notfound");
        Ingredient ingredient = optIngredient.get();
        return ingredientMapper.toIngredientResponseDto(ingredient);
    }

    /**
     * Method to find an Ingredient by it's name in the database.
     * @param name the name on which we query the repository.
     * @return An IngredientResponseDto reflecting the Ingredient found in the database.
     * @throws PizzeriaException when the name is null.
     */
    @Override
    public IngredientResponseDto findByName(String name) throws PizzeriaException {
        if (null == name)
            throw new PizzeriaException("ingredient.name.null", HttpStatus.BAD_REQUEST);
        Optional<Ingredient> optIngredient = ingredientRepository.findByName(name);
        if (optIngredient.isEmpty())
            throw new EntityNotFoundException("ingredient.notfound");
        Ingredient ingredient = optIngredient.get();
        return ingredientMapper.toIngredientResponseDto(ingredient);
    }
}
