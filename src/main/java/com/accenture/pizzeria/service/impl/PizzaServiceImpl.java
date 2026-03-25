package com.accenture.pizzeria.service.impl;

import com.accenture.pizzeria.exception.PizzeriaException;
import com.accenture.pizzeria.mapper.PizzaMapper;
import com.accenture.pizzeria.model.Pizza;
import com.accenture.pizzeria.repository.PizzaRepository;
import com.accenture.pizzeria.service.PizzaService;
import com.accenture.pizzeria.service.dto.PizzaRequestDto;
import com.accenture.pizzeria.service.dto.PizzaResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional
public class PizzaServiceImpl implements PizzaService {

    private final PizzaRepository pizzaDao;
    private final PizzaMapper pizzaMapper;
    private final MessageSourceAccessor messages;


    @Override
    public PizzaResponseDto addPizza(PizzaRequestDto pizzaRequestDto) throws PizzeriaException {
        if (pizzaRequestDto == null)
            throw new PizzeriaException("pizza.dto.null", HttpStatus.BAD_REQUEST);
        Pizza pizza = pizzaMapper.toPizza(pizzaRequestDto);
        pizza.doesPizzaHasIngredients();
        Pizza saved = pizzaDao.save(pizza);
        return pizzaMapper.toPizzaResponseDto(saved);
    }

}
