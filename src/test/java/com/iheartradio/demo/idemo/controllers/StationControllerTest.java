package com.iheartradio.demo.idemo.controllers;

import com.iheartradio.demo.idemo.models.entities.Station;
import com.iheartradio.demo.idemo.services.face.StationService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(value = StationControllerTest.class, secure = false)
public class StationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // Pull in the application context created by @ContextConfiguration
    @Autowired
    private WebApplicationContext wac;

    @MockBean
    private StationService stationService;

    @Before
    public void setUp() throws Exception {
        // Setup MockMVC to use our Spring Configuration
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void createOrUpdateStation_test() throws Exception {
        // given
        Station station = new Station();
        station.setName("Home Town rock");
        station.setHdEnabled(true);
        station.setCallSign("kann");
        station.setStationId(0L);

        // studentService.addCourse to respond back with mockCourse
        Mockito.when(
                stationService.createOrUpdate(Mockito.any(Station.class)))
                .thenReturn(station);

        // when
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/v1/station")
                .accept(MediaType.APPLICATION_JSON).content(station.toString())
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        // then
        assertEquals(HttpStatus.CREATED.value(), response.getStatus());

        assertEquals("http://localhost/api/v1/station",
                response.getHeader(HttpHeaders.LOCATION));

    }

    @Test
    public void retrieveAllStations() {
    }

    @Test
    public void retrieveStationById() throws Exception {
        // given
        Station station = new Station();
        station.setName("Home Town rock");
        station.setHdEnabled(true);
        station.setCallSign("kann");
        station.setStationId(0L);

        Mockito.when(
                stationService.getStationById(Mockito.anyLong()))
                .thenReturn(station);

        // when
        RequestBuilder requestBuilder = get(
                "/api/v1/station/id/0").accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        // then
        System.out.println(result.getResponse());
        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void retrieveStationByName() throws Exception {
        // given
        Station station = new Station();
        station.setName("Home Town rock");
        station.setHdEnabled(true);
        station.setCallSign("kann");
        station.setStationId(0L);

        Mockito.when(
                stationService.getStationById(Mockito.anyLong()))
                .thenReturn(station);

        // when
        MvcResult mvcResult = mockMvc.perform(get("/api/v1/station/name/{name}", "name"))
                .andExpect(status().isOk()).andReturn();
        Assert.assertEquals("name", mvcResult.getResponse().getContentAsString());

        RequestBuilder requestBuilder = get(
                "/api/v1/station/id/0").accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(get("/api/v1/station/name/{name}", "name"))
                .andReturn();

        // then
        System.out.println(result.getResponse());
        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void retrieveStationByHdEnabled() {
    }

    @Test
    public void deleteById() {
    }

    @Test
    public void createOrUpdateStation() {
    }
}