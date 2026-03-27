package com.accenture.pizzeria.service.impl;

import com.accenture.pizzeria.exception.PizzeriaException;
import com.accenture.pizzeria.mapper.CustomerMapper;
import com.accenture.pizzeria.mapper.OrderMapper;
import com.accenture.pizzeria.model.*;
import com.accenture.pizzeria.repository.CustomerRepository;
import com.accenture.pizzeria.repository.OrderRepository;
import com.accenture.pizzeria.service.OrderService;
import com.accenture.pizzeria.service.dto.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.support.MessageSourceAccessor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    private OrderService orderService;
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private OrderMapper orderMapper;
    @Mock
    private CustomerMapper customerMapper;
    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private MessageSourceAccessor msg;

    @BeforeEach
    void setup() {
        orderRepository = Mockito.mock(OrderRepository.class);
        orderMapper = Mockito.mock(OrderMapper.class);
        customerMapper = Mockito.mock(CustomerMapper.class);
        customerRepository = Mockito.mock(CustomerRepository.class);
        msg = Mockito.mock(MessageSourceAccessor.class);
        orderService = new OrderServiceImpl(orderRepository, orderMapper, customerRepository, msg);
    }

    @Test
    @DisplayName("Add a new Order Success")
    void testOrderAddSuccess() throws PizzeriaException {
        Customer customer = new Customer(UUID.randomUUID(), "Joe", "Leblanc", "joe.leblanc@gmail.fr", new ArrayList<>(), new Address(UUID.randomUUID(), "7 rue du Test", "TestVille", "12345"), false);
        CustomerResponseDto customerResponseDto = new CustomerResponseDto(customer.getUuid(), customer.getFirstName(), customer.getLastName(), customer.getEmail(), new AddressDto(customer.getAddress().getStreet(), customer.getAddress().getCity(), customer.getAddress().getPostalCode()), customer.getIsVIP());

        Ingredient tomato = new Ingredient(UUID.randomUUID(), "Tomato", 100);
        Ingredient cheese = new Ingredient(UUID.randomUUID(), "Cheese", 100);
        Ingredient basilic = new Ingredient(UUID.randomUUID(), "Basilic", 100);
        List<Ingredient> ingredients = List.of(tomato, cheese, basilic);

        PizzaRequestDto pizzaSmall = new PizzaRequestDto("Margarita", ESize.SMALL, ingredients, 10.0);

        PizzaRequestDto pizzaMedium = new PizzaRequestDto("Margarita", ESize.MEDIUM, ingredients, 10.0);
        PizzaRequestDto pizzaLarge = new PizzaRequestDto("Margarita", ESize.LARGE, ingredients, 10.0);
        List<PizzaRequestDto> pizzas = List.of(pizzaSmall, pizzaMedium, pizzaLarge);

        OrderRequestDto requestDto = new OrderRequestDto("joe.leblanc@gmail.fr", pizzas);

        Pizza pizzaSmallEntity = new Pizza("Margarita", ESize.SMALL, ingredients, 10.0);
        Pizza pizzaMediumEntity = new Pizza("Margarita", ESize.MEDIUM, ingredients, 10.0);
        Pizza pizzaLargeEntity = new Pizza("Margarita", ESize.LARGE, ingredients, 10.0);
        List<Pizza> pizzasListEntity = List.of(pizzaSmallEntity, pizzaMediumEntity, pizzaLargeEntity);

        Order order = new Order(UUID.randomUUID(), customer, pizzasListEntity, EStatus.PENDING, LocalDateTime.now());

        customer.getOrders().add(order);

        PizzaResponseDto pizzaSmallResponse = new PizzaResponseDto("Margarita", ESize.SMALL, ingredients, 10.0);
        PizzaResponseDto pizzaMediumResponse = new PizzaResponseDto("Margarita", ESize.MEDIUM, ingredients, 10.0);
        PizzaResponseDto pizzaLargeResponse = new PizzaResponseDto("Margarita", ESize.LARGE, ingredients, 10.0);
        List<PizzaResponseDto> pizzasListResponse = List.of(pizzaSmallResponse, pizzaMediumResponse, pizzaLargeResponse);

        OrderResponseDto expectedResponse = new OrderResponseDto(order.getId(), customerResponseDto, pizzasListResponse, EStatus.PENDING, 37.0, LocalDateTime.now());

        when(orderMapper.toOrder(any(OrderRequestDto.class))).thenReturn(order);
        when(orderRepository.save(any(Order.class))).thenReturn(order);
        when(orderMapper.toOrderResponseDto(any(Order.class))).thenReturn(expectedResponse);
        when(customerRepository.findByEmail(any(String.class))).thenReturn(Optional.of(customer));

        OrderResponseDto actual = orderService.addOrder(requestDto);

        Assertions.assertAll(
                () -> Assertions.assertEquals(expectedResponse, actual),
                () -> Assertions.assertEquals(expectedResponse.customer(), actual.customer()),
                () -> Assertions.assertEquals(expectedResponse.pizzas(), actual.pizzas()),
                () -> Assertions.assertEquals(expectedResponse.totalPrice(), actual.totalPrice()),
                () -> Assertions.assertEquals(expectedResponse.status(), actual.status()),
                () -> Assertions.assertEquals(expectedResponse.date(), actual.date()),
                () -> Assertions.assertEquals(order, customer.getOrders().getFirst())
        );
    }

    @Test
    @DisplayName("Add a new Order Fail, dto is null")
    void testOrderFailDtoNull() {
        PizzeriaException pizzeriaException = Assertions.assertThrows(PizzeriaException.class,()->orderService.addOrder(null));
        Assertions.assertNotNull(pizzeriaException);
        Assertions.assertEquals(msg.getMessage("order.dto.null"),pizzeriaException.getMessage());
    }
}
