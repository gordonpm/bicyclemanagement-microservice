package io.gordonpm.bicyclemanagement.controller;

import io.gordonpm.bicyclemanagement.dto.Bicycle;
import io.gordonpm.bicyclemanagement.service.BicycleService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@WebMvcTest()
public class BicycleControllerTest {
    private final Logger logger = LoggerFactory.getLogger(BicycleControllerTest.class);

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BicycleService bicycleService;

    private final Bicycle mockBicycle1 = new Bicycle("500", "Orbea", "Orca", 2000.00);
    private final Bicycle mockBicycle2 = new Bicycle("600", "Trek", "Domane", 5000.00);

    @Test
    @DisplayName("Test for getting bicycle details by id")
    public void getBicycleByIdTest() throws Exception {
        Mockito.when(bicycleService.getBicycle("500"))
               .thenReturn(mockBicycle1);
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
    public void getBicycleByIdNotFoundTest() throws Exception {
        Mockito.when(bicycleService.getBicycle("400"))
                .thenReturn(null);
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/bicycles/400")
                .accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        logger.info(String.valueOf(result.getResponse().getStatus()));

        assertThat(result.getResponse().getStatus()).isEqualTo(404);
    }

    @Test
    @DisplayName("Test for getting all bicycles")
    public void getAllBicyclesTest() throws Exception {
        Mockito.when(bicycleService.getAllBicycles())
                .thenReturn(Arrays.asList(mockBicycle1, mockBicycle2));
        mockMvc.perform(MockMvcRequestBuilders.get("/bicycles"))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", Matchers.is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is("500")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].vendor", Matchers.is("Orbea")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name", Matchers.is("Orca")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].price", Matchers.is(2000.00)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id", Matchers.is("600")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].vendor", Matchers.is("Trek")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name", Matchers.is("Domane")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].price", Matchers.is(5000.00)));
    }

    @Test
    @DisplayName("Test for creating new bicycle")
    public void createNewBicycleTest() throws Exception {
        String bicycleJson = "{\"id\":\"500\",\"vendor\":\"Orbea\",\"name\":\"Orca\",\"price\":2000.00}";
        Mockito.when(bicycleService.addBicycle(Mockito.any(Bicycle.class)))
                .thenReturn(mockBicycle1);
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/bicycles")
                .accept(MediaType.APPLICATION_JSON)
                .content(bicycleJson)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        logger.info(String.valueOf(response.getStatus()));

        assertThat(response.getStatus()).isEqualTo(201);
        assertEquals("http://localhost/bicycles/500",
                response.getHeader(HttpHeaders.LOCATION));
    }

    @Test
    @DisplayName("Negative test for creating new bicycle with empty name")
    public void createNewBicycleWithInvalidNameTest() throws Exception {
        final String bicycleJson = "{\"id\":\"500\",\"vendor\":\"Orbea\",\"name\":\"\",\"price\":2200.00}";
        Mockito.when(bicycleService.addBicycle(Mockito.any(Bicycle.class)))
                .thenReturn(null);
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/bicycles")
                .accept(MediaType.APPLICATION_JSON)
                .content(bicycleJson)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        logger.info(String.valueOf(response.getStatus()));

        assertThat(response.getStatus()).isEqualTo(400);
    }

    @Test
    @DisplayName("Test for updating existing bicycle")
    public void updateBicycleTest() throws Exception {
        final String bicycleJson = "{\"id\":\"500\",\"vendor\":\"Orbea\",\"name\":\"Orca\",\"price\":2500.00}"; // updated price
        final Bicycle mockUpdatedBicycle = new Bicycle("500", "Orbea", "Orca", 2500.00);
        Mockito.when(bicycleService.updateBicycle(Mockito.anyString(), Mockito.any(Bicycle.class)))
                .thenReturn(mockUpdatedBicycle);
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/bicycles/500")
                .accept(MediaType.APPLICATION_JSON)
                .content(bicycleJson)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is("500")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.vendor", Matchers.is("Orbea")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is("Orca")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price", Matchers.is(2500.00)));
    }

    @Test
    @DisplayName("Test for deleting existing bicycle")
    public void deleteBicycleTest() throws Exception {
        Mockito.when(bicycleService.deleteBicycle("500"))
                .thenReturn(true);
        mockMvc.perform(MockMvcRequestBuilders.delete("/bicycles/500"))
                .andExpect(MockMvcResultMatchers.status().is(204));
    }

    @Test
    @DisplayName("Test for deleting bicycle which does not exit")
    public void deleteUnknownBicycleTest() throws Exception {
        Mockito.when(bicycleService.deleteBicycle("500"))
                .thenReturn(false);
        mockMvc.perform(MockMvcRequestBuilders.delete("/bicycles/500"))
                .andExpect(MockMvcResultMatchers.status().is(404));
    }
}
