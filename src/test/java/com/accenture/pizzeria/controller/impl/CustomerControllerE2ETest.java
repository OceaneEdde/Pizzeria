package com.accenture.pizzeria.controller.impl;

import com.accenture.pizzeria.service.dto.AddressDto;
import com.accenture.pizzeria.service.dto.CustomerRequestDto;
import com.accenture.pizzeria.service.dto.CustomerResponseDto;
import com.accenture.pizzeria.service.impl.CustomerServiceImpl;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.TestRestTemplate;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureTestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertAll;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestRestTemplate
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CustomerControllerE2ETest {
    private static final String API_CUSTOMERS_ENDPOINT = "/customers";
    private static final String BASE_STREET = "12 rue du Test";
    private static final String BASE_CITY = "Brest";
    private static final String BASE_POSTAL_CODE = "29200";

    private static final String BASE_FIRST_NAME = "Jean";
    private static final String BASE_LAST_NAME = "Petit";
    private static final String BASE_EMAIL = "jean.petit@customer.com";
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CustomerServiceImpl customerService;

    // TODO : Vérifier le fonctionnement de ce test une fois ORDER implémenté
    @Test
    @DisplayName("Creates a Customer through POST endpoint")
    @Order(1)
    void testPostCustomerSuccess() {
        AddressDto addressDto = new AddressDto(BASE_STREET, BASE_CITY, BASE_POSTAL_CODE);
        CustomerRequestDto requestDto = new CustomerRequestDto(BASE_FIRST_NAME, BASE_LAST_NAME, BASE_EMAIL, addressDto);

        ResponseEntity<Void> response = restTemplate.postForEntity(String.format("http://localhost:%s%s", port, API_CUSTOMERS_ENDPOINT), requestDto, Void.class);

        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode(), "POST customer must return a 201 HTTP Response Code");
    }

    @Test
    @DisplayName("Find a Customer through GET endpoint, method : findByEmail")
    @Order(2)
    void testGetCustomerByEmail() {
        AddressDto addressDto = new AddressDto(BASE_STREET, BASE_CITY, BASE_POSTAL_CODE);
        CustomerRequestDto requestDto = new CustomerRequestDto(BASE_FIRST_NAME, BASE_LAST_NAME, BASE_EMAIL, addressDto);

        ResponseEntity<CustomerResponseDto> response = restTemplate.exchange(String.format("http://localhost:%s%s/email/%s", port, API_CUSTOMERS_ENDPOINT, BASE_EMAIL), HttpMethod.GET, null, new ParameterizedTypeReference<>() {
        });

        CustomerResponseDto responseDto = response.getBody();

        assertAll(
                () -> Assertions.assertEquals(HttpStatus.OK, response.getStatusCode(), "GET customer must return a 200 HTTP Response Code"),
                () -> Assertions.assertNotNull(responseDto, "DtoResponse should not be null"),
                () -> Assertions.assertNotNull(responseDto.uuid(), "Id should not be null"),
                () -> Assertions.assertNotNull(responseDto.firstName(), "firstName should not be null"),
                () -> Assertions.assertEquals(requestDto.firstName(), responseDto.firstName(), "firstName should be the same"),
                () -> Assertions.assertNotNull(responseDto.lastName(), "lastName should not be null"),
                () -> Assertions.assertEquals(requestDto.lastName(), responseDto.lastName(), "lastName should be the same"),
                () -> Assertions.assertNotNull(responseDto.email(), "email should not be null"),
                () -> Assertions.assertEquals(requestDto.email(), responseDto.email(), "email should be the same"),
                () -> Assertions.assertNotNull(responseDto.address(), "address should not be null"),
                () -> Assertions.assertNotNull(responseDto.address().street(), "Address's street should not be null"),
                () -> Assertions.assertEquals(requestDto.address().street(), responseDto.address().street(), "Address's street should be the same"),
                () -> Assertions.assertNotNull(responseDto.address().city(), "Address's city should not be null"),
                () -> Assertions.assertEquals(requestDto.address().city(), responseDto.address().city(), "Address's city should be the same"),
                () -> Assertions.assertNotNull(responseDto.address().postalCode(), "Address's postalCode should not be null"),
                () -> Assertions.assertEquals(requestDto.address().postalCode(), responseDto.address().postalCode(), "Address's postalCode should be the same"),
                () -> Assertions.assertNotNull(responseDto.isVIP(), "isVip should not be null"),
                () -> Assertions.assertEquals(false, responseDto.isVIP(), "isVip should be false")
        );
    }
}
