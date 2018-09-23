package com.iheartradio.demo.idemo.services.face;

import com.iheartradio.demo.idemo.models.entities.Station;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface StationService {
    List<Station> getAllStations();
    Station getStationById(Long id);
    Station getStationByName(String name);
    List<Station> getStationsByHdEnabled(Boolean enabled);
    void deleteById(long id);
    Station createOrUpdate(Station station);
}
