package com.accenture.pizzeria.service.impl;

import com.accenture.pizzeria.exception.PizzeriaException;
import com.accenture.pizzeria.mapper.PizzaMapper;
import com.accenture.pizzeria.model.Pizza;
import com.accenture.pizzeria.repository.PizzaRepository;
import com.accenture.pizzeria.service.PizzaService;
import com.accenture.pizzeria.service.dto.PizzaRequestDto;
import com.accenture.pizzeria.service.dto.PizzaResponseDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class PizzaServiceImpl implements PizzaService {

    private final PizzaRepository pizzaDao;
    private final PizzaMapper pizzaMapper;
    private final MessageSourceAccessor messages;

    /**
     * Service method to add a new pizza in the database
     * @param pizzaRequestDto a PizzaRequestDto
     * @return a PizzaResponseDto, reflecting the newly created Pizza
     * @throws PizzeriaException when the DTO is malformed
     */
    @Override
    public PizzaResponseDto addPizza(PizzaRequestDto pizzaRequestDto) throws PizzeriaException {
        log.info("Accessing PIZZA Service Method : addPizza");
        if (pizzaRequestDto == null)
            throw new PizzeriaException("pizza.dto.null", HttpStatus.BAD_REQUEST);
        Pizza pizza = pizzaMapper.toPizza(pizzaRequestDto);
        pizza.doesPizzaHasIngredients();
        Pizza saved = pizzaDao.save(pizza);
        return pizzaMapper.toPizzaResponseDto(saved);
    }

}
