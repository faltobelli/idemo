package com.iheartradio.demo.idemo.services.impl;

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

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
public class StationServiceImplTest {

    private static final String HOT_ROCK_NAME = "hot rock";
    private StationService stationService;
    @MockBean
    StationRepository stationRepository;

//    private List<Station> mockStationList;

    @Before
    public void setUp() throws Exception {
        List<Station> mockStationList = new ArrayList<>();
        Station station = new Station(); station.setName("Home Town rock"); station.setHdEnabled(true); station.setCallSign("kann");  station.setStationId(0L);
        mockStationList.add(station);
        station = new Station(); station.setName(HOT_ROCK_NAME); station.setHdEnabled(true); station.setCallSign("wrox"); station.setStationId(1L);
        mockStationList.add(station);
        station = new Station(); station.setName("country & western"); station.setHdEnabled(true); station.setCallSign("kcnw"); station.setStationId(2L);
        mockStationList.add(station);
        station = new Station(); station.setName("interesting talk"); station.setHdEnabled(false); station.setCallSign("ktlk");  station.setStationId(3L);
        mockStationList.add(station);

        when(stationRepository.findByName(Mockito.eq(HOT_ROCK_NAME)))
                .thenReturn(mockStationList.stream()
                            .filter(s -> s.getName().equalsIgnoreCase(HOT_ROCK_NAME))
                            .findFirst());
        when(stationRepository.findAll()).thenReturn(mockStationList);


        stationService = new StationServiceImpl(stationRepository);
    }

    @Test
    public void getAllStations_happyPath_test() {
        // given
        // when
        List<Station> stations = stationService.getAllStations();
        // then
        assertNotNull(stations);
        assertTrue(!stations.isEmpty());
    }

    @Test
    public void getStationById_happyPath_test() {
        // given
        // when
        // then
    }

    @Test
    public void getStationByName_happyPath_test() {
        // given
        // when
        Station station = stationService.getStationByName(HOT_ROCK_NAME);
        // then
        assertTrue(station.getName().equalsIgnoreCase(HOT_ROCK_NAME));
    }

    @Test
    public void getStationsByHdEnabled_happyPath_test() {
        // given
        // when
        // then
    }

    @Test
    public void deleteById_happyPath_test() {
        // given
        // when
        // then
    }

    @Test
    public void createOrUpdate_happyPath_test() {
        // given
        // when
        // then
    }
}