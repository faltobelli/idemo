package com.iheartradio.demo.idemo.services.impl;

import com.iheartradio.demo.idemo.exceptions.StationNotFoundException;
import com.iheartradio.demo.idemo.models.entities.Station;
import com.iheartradio.demo.idemo.repositories.face.StationRepository;
import com.iheartradio.demo.idemo.services.face.StationService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
public class StationServiceImplTest {

    private static final String HOT_ROCK_NAME = "hot rock";
    private static final String ANY_OLD_STATION = "any old station";
    private StationService stationService;
    @MockBean
    StationRepository stationRepository;

    private List<Station> mockStationList;

    @Before
    public void setUp() throws Exception {
        mockStationList = new ArrayList<>();
        Station station = new Station(); station.setName("Home Town rock"); station.setHdEnabled(true); station.setCallSign("kann");  station.setStationId(0L);
        mockStationList.add(station);
        station = new Station(); station.setName(HOT_ROCK_NAME); station.setHdEnabled(true); station.setCallSign("wrox"); station.setStationId(1L);
        mockStationList.add(station);
        station = new Station(); station.setName("country & western"); station.setHdEnabled(true); station.setCallSign("kcnw"); station.setStationId(2L);
        mockStationList.add(station);
        station = new Station(); station.setName("interesting talk"); station.setHdEnabled(false); station.setCallSign("ktlk");  station.setStationId(3L);
        mockStationList.add(station);

        stationService = new StationServiceImpl(stationRepository);
    }

    @Test
    public void getAllStations_happyPath_test() {
        // given
        when(stationRepository.findAll()).thenReturn(mockStationList);
        // when
        List<Station> stations = stationService.getAllStations();
        // then
        assertNotNull(stations);
        assertTrue(!stations.isEmpty());
    }

    @Test
    public void getStationById_happyPath_test() {
        // given
        when(stationRepository.findById(Mockito.eq(1L))).thenReturn(mockStationList.stream().filter(s -> s.getStationId() == 1L).findFirst());

        // when
        Station station = stationService.getStationById(1L);
        // then
        assertTrue(station.getName().equalsIgnoreCase(HOT_ROCK_NAME));
    }

    @Test(expected = StationNotFoundException.class)
    public void getStationById_notFound_test() {
        // given
        when(stationRepository.findById(Mockito.eq(25L))).thenReturn(Optional.empty());
        // when
        Station station = stationService.getStationById(25L);
        // then
        fail();
    }

    @Test
    public void getStationByName_happyPath_test() {
        // given
        when(stationRepository.findByName(Mockito.eq(HOT_ROCK_NAME))).thenReturn(mockStationList.stream().filter(s -> s.getName().equalsIgnoreCase(HOT_ROCK_NAME)).findFirst());
        // when
        Station station = stationService.getStationByName(HOT_ROCK_NAME);
        // then
        assertTrue(station.getName().equalsIgnoreCase(HOT_ROCK_NAME));
    }

    @Test(expected = StationNotFoundException.class)
    public void getStationByName_notFound_test() {
        // given
        when(stationRepository.findByName(Mockito.eq(ANY_OLD_STATION))).thenReturn(Optional.empty());
        // when
        Station station = stationService.getStationByName(ANY_OLD_STATION);
        // then
        fail();
    }

    @Test
    public void getStationsByHdEnabled_true_happyPath_test() {
        // given
        when(stationRepository.findByHdEnabledOrderByCallSign(Mockito.eq(true))).thenReturn(mockStationList.stream().filter(s -> s.isHdEnabled() == true).collect(Collectors.toList()));
        // when
        List<Station> stations = stationService.getStationsByHdEnabled(true);
        // then
        assertNotNull(stations);
        assertTrue(!stations.isEmpty());
        assertTrue(stations.size() == 3);
    }

    @Test
    public void getStationsByHdEnabled_false_happyPath_test() {
        // given
        when(stationRepository.findByHdEnabledOrderByCallSign(Mockito.eq(false))).thenReturn(mockStationList.stream().filter(s -> s.isHdEnabled() == false).collect(Collectors.toList()));
        // when
        List<Station> stations = stationService.getStationsByHdEnabled(false);
        // then
        assertNotNull(stations);
        assertTrue(!stations.isEmpty());
        assertTrue(stations.size() == 1);
    }

    @Test
    public void deleteById_Does_happyPath_test() {
        // given
        when(stationRepository.existsById(Mockito.eq(2L))).thenReturn(true);
        // when
        stationService.deleteById(2L);
        // then
        assertTrue(true);
    }

    @Test(expected = StationNotFoundException.class)
    public void deleteById_Does_NotThrowException_test() {
        // given
        when(stationRepository.existsById(Mockito.eq(30L))).thenReturn(false);
        // when
        stationService.deleteById(30L);
        // then
        fail();
    }

}