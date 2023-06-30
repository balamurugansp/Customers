package com.example.customer.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.example.customer.entity.Customer;
import com.example.customer.service.customerService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(value = customerController.class)
@WithMockUser
public class customerControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private customerService customerService;

	@Autowired
	private ObjectMapper objectMapper;

	Customer customer = new Customer(5l, "BALA", "MURUGAN", "spbalamurugan@gmail.com", "+919003030909");
	String customerJson = "{\"firstname\":\"balamurugan\",\"lastname\":\"sengoden\",\"email\":\"spbalamurugan@gmail.com\",\"phoneNumber\":\"+919003030909\" }";

	@Test
	public void addCustomerTest() throws Exception {
		Mockito.when(customerService.addCustomer(Mockito.any(Customer.class))).thenReturn(customer);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/customer/addcustomer")
				.accept(MediaType.APPLICATION_JSON).content(customerJson).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.CREATED.value(), response.getStatus());

	}

	@Test
	public void getCustomerTest() throws Exception {

		long customerId = 51L;
		Customer customer = Customer.builder().firstName("bala").lastName("murugan").email("spbalamurugan@gmail.com")
				.phoneNumber("+919003030909").build();
		given(customerService.getCustomerById(customerId)).willReturn(customer);
		ResultActions response = mockMvc.perform(get("/api/customer/getCustomerbyid/{id}", customerId));
		response.andExpect(status().isOk()).andDo(print())
				.andExpect(jsonPath("$.firstName", is(customer.getFirstName())))
				.andExpect(jsonPath("$.lastName", is(customer.getLastName())))
				.andExpect(jsonPath("$.email", is(customer.getEmail())));

	}

	@Test
	public void getAllcutomersTest() throws Exception {
		List<Customer> customerList = new ArrayList<>();
		customerList.add(Customer.builder().firstName("bala").lastName("murugan2").email("spbalamurugan2@gmail.com")
				.phoneNumber("+919503030909").build());
		customerList.add(Customer.builder().firstName("bala1").lastName("murugan3").email("spbalamurugan4@gmail.com")
				.phoneNumber("+919403030909").build());
		customerList.add(Customer.builder().firstName("bala2").lastName("murugan4").email("spbalamurugan5@gmail.com")
				.phoneNumber("+919303030909").build());
		customerList.add(Customer.builder().firstName("bala3").lastName("murugan5").email("spbalamurugan3@gmail.com")
				.phoneNumber("+919103030909").build());
		customerList.add(Customer.builder().firstName("bala4").lastName("murugan6").email("spbalamurugan1@gmail.com")
				.phoneNumber("+919203030909").build());
		given(customerService.getAllCustomers()).willReturn(customerList);
		ResultActions response = mockMvc.perform(get("/api/customer/getCustomers"));
		response.andExpect(status().isOk()).andDo(print()).andExpect(jsonPath("$.size()", is(customerList.size())));
	}

	@Test
	public void updateCustomerTest() throws Exception {
		long customerId = 51L;
		Customer existingCustomer = Customer.builder().firstName("bala").lastName("murugan")
				.email("spbalamurugan@gmail.com").phoneNumber("+919003030909").build();
		Customer updateCustomer = Customer.builder().firstName("ramesh").lastName("kannan")
				.email("ramesh.kannan@gmail.com").phoneNumber("+919739326057").build();
		given(customerService.getCustomerById(customerId)).willReturn(existingCustomer);
		given(customerService.updateCustomer(any(Customer.class)))
				.willAnswer((invocation) -> invocation.getArgument(0));

		ResultActions response = mockMvc.perform(put("/api/customer/updateCustomer/{id}", customerId)
				.contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(updateCustomer)));

		response.andExpect(status().isOk()).andDo(print())
				.andExpect(jsonPath("$.firstName", is(updateCustomer.getFirstName())))
				.andExpect(jsonPath("$.lastName", is(updateCustomer.getLastName())))
				.andExpect(jsonPath("$.email", is(updateCustomer.getEmail())));
	}

	@Test
	public void deleteCustomerTest() throws Exception {
		long customerId = 51L;
		Customer existingCustomer = Customer.builder().firstName("bala").lastName("murugan")
				.email("spbalamurugan@gmail.com").phoneNumber("+919003030909").build();
		given(customerService.getCustomerById(customerId)).willReturn(existingCustomer);
		willDoNothing().given(customerService).deleteCustomer(customerId);
		ResultActions response = mockMvc.perform(delete("/api/customer/deletecustomer/{id}", customerId));
		response.andExpect(status().is4xxClientError()).andDo(print());

	}

}
