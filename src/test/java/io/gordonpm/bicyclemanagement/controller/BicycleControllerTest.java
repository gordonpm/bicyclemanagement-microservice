package io.gordonpm.bicyclemanagement.controller;

import io.gordonpm.bicyclemanagement.dto.Bicycle;
import io.gordonpm.bicyclemanagement.service.BicycleService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@WebMvcTest
public class BicycleControllerTest {
    Logger logger = LoggerFactory.getLogger(BicycleControllerTest.class);

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BicycleService bicycleService;

    private Bicycle mockBicycle = new Bicycle("500", "Orbea", "Orca", 2000.00);

    @Test
    @DisplayName("Test for getting bicycle details by id")
    public void getBicycleByIdTest() throws Exception {
        Mockito.when(bicycleService.getBicycle("500"))
               .thenReturn(mockBicycle);
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/bicycles/500")
                .accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        logger.info(result.getResponse().getContentAsString());
        logger.info(String.valueOf(result.getResponse().getStatus()));

        String expected = "{id:\"500\",vendor:Orbea,name:Orca,price:2000.00}";
        JSONAssert.assertEquals(expected, result.getResponse()
                .getContentAsString(), false);
    }

    @Test
    @DisplayName("Test for 404 status code when bicycle id not found")
    public void getBicycleByIdNotFound() throws Exception {
        Mockito.when(bicycleService.getBicycle("400"))
                .thenReturn(null);
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/bicycles/400")
                .accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        logger.info(String.valueOf(result.getResponse().getStatus()));

        assertThat(result.getResponse().getStatus()).isEqualTo(404);
    }
}
