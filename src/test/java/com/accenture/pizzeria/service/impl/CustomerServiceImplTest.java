package com.accenture.pizzeria.service.impl;

import com.accenture.pizzeria.exception.PizzeriaException;
import com.accenture.pizzeria.mapper.AddressMapper;
import com.accenture.pizzeria.mapper.CustomerMapper;
import com.accenture.pizzeria.model.Address;
import com.accenture.pizzeria.model.Customer;
import com.accenture.pizzeria.repository.CustomerRepository;
import com.accenture.pizzeria.service.CustomerService;
import com.accenture.pizzeria.service.dto.AddressDto;
import com.accenture.pizzeria.service.dto.CustomerRequestDto;
import com.accenture.pizzeria.service.dto.CustomerResponseDto;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.springframework.context.support.MessageSourceAccessor;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CustomerServiceImplTest {
    private static final String BASE_STREET = "12 rue du Test";
    private static final String BASE_CITY = "Brest";
    private static final String BASE_POSTAL_CODE = "29200";

    private static final String BASE_FIRST_NAME = "Jean";
    private static final String BASE_LAST_NAME = "Petit";
    private static final String BASE_EMAIL = "jean.petit@customer.com";
    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private CustomerMapper customerMapper;
    @Mock
    private AddressMapper addressMapper;
    @Mock
    private MessageSourceAccessor messages;

    private CustomerService customerService;

    @BeforeEach
    void setUp() {
        customerRepository = mock(CustomerRepository.class);
        customerMapper = mock(CustomerMapper.class);
        addressMapper = mock(AddressMapper.class);
        messages = mock(MessageSourceAccessor.class);
        customerService = new CustomerServiceImpl(customerRepository, customerMapper, messages);
    }

    @Nested
    class TestCustomerAdd {
        @Test
        @DisplayName("Add a customer success")
        void testCustomerAddSuccess() throws PizzeriaException {
            CustomerService spy = spy(customerService);
            UUID addressId = UUID.randomUUID();
            AddressDto addressDto = new AddressDto(BASE_STREET, BASE_CITY, BASE_POSTAL_CODE);
            Address address = new Address(addressId, BASE_STREET, BASE_CITY, BASE_POSTAL_CODE);

            UUID customerId = UUID.randomUUID();
            Boolean isVip = false;
            CustomerRequestDto requestDto = new CustomerRequestDto(BASE_FIRST_NAME, BASE_LAST_NAME, BASE_EMAIL, addressDto);
            CustomerResponseDto expectedResponseDto = new CustomerResponseDto(customerId, BASE_FIRST_NAME, BASE_LAST_NAME, BASE_EMAIL, addressDto, isVip);

            Customer customerEntity = new Customer(customerId, BASE_FIRST_NAME, BASE_LAST_NAME, BASE_EMAIL, address);

            when(addressMapper.toEntity(any(AddressDto.class))).thenReturn(address);
            when(customerMapper.toEntity(any(CustomerRequestDto.class))).thenReturn(customerEntity);
            when(customerRepository.save(any(Customer.class))).thenReturn(customerEntity);
            when(addressMapper.toDto(any(Address.class))).thenReturn(addressDto);
            when(customerMapper.toResponseDto(any(Customer.class))).thenReturn(expectedResponseDto);

            CustomerResponseDto actualResponseDto = assertDoesNotThrow(() -> spy.addCustomer(requestDto));

            Assertions.assertAll(
                    () -> Assertions.assertNotNull(actualResponseDto, "DtoResponse should not be null"),
                    () -> Assertions.assertNotNull(actualResponseDto.id(), "Id should not be null"),
                    () -> Assertions.assertNotNull(actualResponseDto.firstName(), "firstName should not be null"),
                    () -> Assertions.assertEquals(expectedResponseDto.firstName(), actualResponseDto.firstName(), "firstName should be the same"),
                    () -> Assertions.assertNotNull(actualResponseDto.lastName(), "lastName should not be null"),
                    () -> Assertions.assertEquals(expectedResponseDto.lastName(), actualResponseDto.lastName(), "lastName should be the same"),
                    () -> Assertions.assertNotNull(actualResponseDto.email(), "email should not be null"),
                    () -> Assertions.assertEquals(expectedResponseDto.email(), actualResponseDto.email(), "email should be the same"),
                    () -> Assertions.assertNotNull(actualResponseDto.address(), "address should not be null"),
                    () -> Assertions.assertNotNull(actualResponseDto.address().street(), "Address's street should not be null"),
                    () -> Assertions.assertNotNull(actualResponseDto.address().city(), "Address's city should not be null"),
                    () -> Assertions.assertNotNull(actualResponseDto.address().postalCode(), "Address's postalCode should not be null"),
                    () -> Assertions.assertNotNull(actualResponseDto.isVip(), "isVip should not be null"),
                    () -> Assertions.assertEquals(expectedResponseDto.isVip(), actualResponseDto.isVip(), "isVip should be the same")
            );

            verify(spy, times(1)).verify(any(CustomerRequestDto.class));
        }

        @Test
        @DisplayName("Add a customer fail, throws PizzeriaException")
        void testCustomerAddFailThrows() throws PizzeriaException {
            CustomerService spy = spy(customerService);
            PizzeriaException pizzeriaException = assertThrows(PizzeriaException.class, () -> spy.addCustomer(null));
            assertEquals(messages.getMessage("dto.null"), pizzeriaException.getMessage());
            verify(spy, times(1)).verify(any());
        }
    }
}