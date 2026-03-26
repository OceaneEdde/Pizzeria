package com.accenture.pizzeria.controller.impl;

import com.accenture.pizzeria.exception.PizzeriaException;
import com.accenture.pizzeria.mapper.CustomerMapper;
import com.accenture.pizzeria.service.dto.AddressDto;
import com.accenture.pizzeria.service.dto.CustomerRequestDto;
import com.accenture.pizzeria.service.dto.CustomerResponseDto;
import com.accenture.pizzeria.service.impl.CustomerServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import tools.jackson.databind.ObjectMapper;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CustomerController.class)
class CustomerControllerIntegrationTest {

    private static final String API_CUSTOMERS_ENDPOINT = "/customers";
    private static final String BASE_STREET = "12 rue du Test";
    private static final String BASE_CITY = "Brest";
    private static final String BASE_POSTAL_CODE = "29200";

    private static final String BASE_FIRST_NAME = "Jean";
    private static final String BASE_LAST_NAME = "Petit";
    private static final String BASE_EMAIL = "jean.petit@customer.com";
    private static final String BLANK_STRING = "    ";


    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private CustomerServiceImpl customerService;
    @MockitoBean
    private CustomerMapper customerMapper;

    @Nested
    class TestPost{
        @Test
        @DisplayName("Test to persist a customer into the H2 database")
        void postCustomerSuccess() throws Exception {
            AddressDto addressDto = new AddressDto(BASE_STREET,BASE_CITY,BASE_POSTAL_CODE);
            CustomerRequestDto dto = new CustomerRequestDto(BASE_FIRST_NAME,BASE_LAST_NAME,BASE_EMAIL,addressDto);
            CustomerResponseDto responseDto = new CustomerResponseDto(UUID.randomUUID(), BASE_FIRST_NAME,BASE_LAST_NAME,BASE_EMAIL,addressDto,false);

            doReturn(responseDto).when(customerService).addCustomer(dto);

            mockMvc.perform(MockMvcRequestBuilders.post(API_CUSTOMERS_ENDPOINT)
                            .with(csrf())
                            .with(user("test").roles("USER"))
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(new ObjectMapper().writeValueAsString(dto)))
                    .andExpect(status().isCreated());
        }

        @Test
        @DisplayName("Test to persist a customer into the H2 database, fail : bad request")
        void postCustomerFailBadRequest() throws Exception {

            AddressDto addressDto = new AddressDto(BASE_STREET,BASE_CITY,BASE_POSTAL_CODE);
            CustomerRequestDto dto = new CustomerRequestDto(BLANK_STRING,BASE_LAST_NAME,BASE_EMAIL,addressDto);

            doThrow(PizzeriaException.class).when(customerService).addCustomer(dto);

            mockMvc.perform(MockMvcRequestBuilders.post(API_CUSTOMERS_ENDPOINT)
                            .with(csrf())
                            .with(user("test").roles("USER"))
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(new ObjectMapper().writeValueAsString(dto)))
                    .andExpect(status().isBadRequest());
        }
    }

    @Nested
    class TestGet{
        @Nested
        class TestFindByEmail{
            @Test
            @DisplayName("Test to find a customer by it's email in the H2 database, success")
            void testFinByEmailSuccess() throws Exception {
                AddressDto addressDto = new AddressDto(BASE_STREET,BASE_CITY,BASE_POSTAL_CODE);
                CustomerResponseDto responseDto = new CustomerResponseDto(UUID.randomUUID(), BASE_FIRST_NAME,BASE_LAST_NAME,BASE_EMAIL,addressDto,false);

                doReturn(responseDto).when(customerService).findByEmail(any(String.class));

                mockMvc.perform(MockMvcRequestBuilders.get(API_CUSTOMERS_ENDPOINT+"/email/"+BASE_EMAIL)
                                .with(csrf())
                                .with(user("test").roles("USER"))
                                .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk());
            }

            @Test
            @DisplayName("Test to find a customer by it's email in the H2 database, fail, email is blank")
            void testFindByEmailFailEmailBlank() throws Exception {

                doThrow(PizzeriaException.class).when(customerService).findByEmail(BLANK_STRING);

                mockMvc.perform(MockMvcRequestBuilders.get(API_CUSTOMERS_ENDPOINT+"/email/"+BLANK_STRING)
                                .with(csrf())
                                .with(user("test").roles("USER"))
                                .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isBadRequest());
            }
        }
    }

}