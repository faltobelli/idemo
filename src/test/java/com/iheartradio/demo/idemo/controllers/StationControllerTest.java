package com.iheartradio.demo.idemo.controllers;

import com.iheartradio.demo.idemo.exceptions.StationNotFoundException;
import com.iheartradio.demo.idemo.models.entities.Station;
import com.iheartradio.demo.idemo.services.face.StationService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringJUnit4ClassRunner.class)
public class StationControllerTest {

    private static final String STATION_ONE_NAME = "Hot Talk";
    private static final Long INVALID_STATION_ID = 25L;
    private static final String INVALID_STATION_NAME = "Hot Smooth Talk";
    private static final String JAZZ_STATION_NAME = "Smooth Jazzzzz";
    @MockBean
    private StationService stationService;

    private StationController stationController;
    private List<Station> mockStations;

    @Before
    public void setUp() throws Exception {
        stationController = new StationController(stationService);
        mockStations = new ArrayList<>();

        Station station = new Station();
        station.setName("Home Town rock");
        station.setHdEnabled(true);
        station.setCallSign("kann");
        station.setStationId(0L);
        mockStations.add(station);

        station = new Station();
        station.setName(STATION_ONE_NAME);
        station.setHdEnabled(true);
        station.setCallSign("wann");
        station.setStationId(1L);
        mockStations.add(station);

        station = new Station();
        station.setName(JAZZ_STATION_NAME);
        station.setHdEnabled(false);
        station.setCallSign("kjaz");
        station.setStationId(2L);
        mockStations.add(station);

        when(stationService.getAllStations()).thenReturn(mockStations);
        // station by id
        when(stationService.getStationById(Mockito.eq(1L))).thenReturn(mockStations.get(1));
        when(stationService.getStationById(Mockito.eq(INVALID_STATION_ID))).thenThrow(new StationNotFoundException(String.format("Station ID %d was not found.", INVALID_STATION_ID)));
        // station by name
        when(stationService.getStationByName(Mockito.eq(STATION_ONE_NAME))).thenReturn(mockStations.get(1));
        when(stationService.getStationByName(Mockito.eq(INVALID_STATION_NAME))).thenThrow(new StationNotFoundException(String.format("Station ID %d was not found.", INVALID_STATION_ID)));
        // station by HdEnabled
        when(stationService.getStationsByHdEnabled(Mockito.eq(true))).thenReturn(mockStations.stream().filter(s -> s.isHdEnabled() == true).collect(Collectors.toList()));
        when(stationService.getStationsByHdEnabled(Mockito.eq(false))).thenReturn(mockStations.stream().filter(s -> s.isHdEnabled() == false).collect(Collectors.toList()));
    }

    @Test
    public void retrieveAllStations_happyPath_test() throws Exception {
        // given

        // when
        List<Station> response = stationController.retrieveAllStations();

        // then
        assertNotNull(response);
        assertTrue(!response.isEmpty());
    }

    @Test
    public void retrieveStationById_happyPath_test() {
        // given
        // when
        Station station = stationController.retrieveStationById(1L);

        // then
        assertNotNull(station);
        assertTrue(station.getName().compareTo(STATION_ONE_NAME) == 0);
    }

    @Test(expected = StationNotFoundException.class)
    public void retrieveStationById_notFound_test() {
        // given
        // when
        Station station = stationController.retrieveStationById(INVALID_STATION_ID);
        // then
    }

    @Test
    public void retrieveStationByName_happyPath_test() {
        // given
        // when
        Station station = stationController.retrieveStationByName(STATION_ONE_NAME);

        // then
        assertNotNull(station);
        assertTrue(station.getName().compareTo(STATION_ONE_NAME) == 0);
    }

    @Test(expected = StationNotFoundException.class)
    public void retrieveStationByName_notFound_test() {
        // given
        // when
        Station station = stationController.retrieveStationByName(INVALID_STATION_NAME);
        // then
    }

    @Test
    public void retrieveStationByHdEnabled_true_test() {
        // given
        // when
        List<Station> stations = stationController.retrieveStationByHdEnabled(true);

        // then
        assertNotNull(stations);
        assertTrue(!stations.isEmpty());
        assertTrue(stations.size() == 2);
    }

    @Test
    public void retrieveStationByHdEnabled_false_test() {
        // given
        // when
        List<Station> stations = stationController.retrieveStationByHdEnabled(false);

        // then
        assertNotNull(stations);
        assertTrue(!stations.isEmpty());
        assertTrue(stations.size() == 1);
    }

    @Test(expected = StationNotFoundException.class)
    public void deleteById_idDoesNotExist_test() {
        // given
        doThrow(new StationNotFoundException(String.format("Station ID %d was not found.", INVALID_STATION_ID)))
                .when(stationService)
                .deleteById(isA(Long.class));
        // when
        stationController.deleteById(100L);

        // then
        fail();
    }

    @Test
    public void createOrUpdateStation_happyPath_test() {
        // given
        Station station = new Station();
        when(stationService.createOrUpdate(Mockito.any(Station.class)))
                .thenReturn(station);

        // when
        Boolean ret = stationController.createOrUpdateStation(station);

        // then
        assertTrue(ret);
    }

}