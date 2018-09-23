package com.iheartradio.demo.idemo.services.impl;

import com.iheartradio.demo.idemo.exceptions.StationNotFoundException;
import com.iheartradio.demo.idemo.models.entities.Station;
import com.iheartradio.demo.idemo.repositories.face.StationRepository;
import com.iheartradio.demo.idemo.services.face.StationService;
import javassist.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StationServiceImpl implements StationService {

    private StationRepository stationRepository;

    public StationServiceImpl(StationRepository stationRepository) {
        this.stationRepository = stationRepository;
    }


    @Override
    public List<Station> getAllStations() {
        return null;
    }

    @Override
    public Station getStationById(Long id) {
        Optional<Station> station = stationRepository.findById(id);

        if(!station.isPresent()) {
            throw new StationNotFoundException(String.format("Station ID %d was not found.", id));

        }

        return station.get();    }

    @Override
    public Station getStationByName(String name) {
        Optional<Station> station = stationRepository.findByName(name);

        if(!station.isPresent()) {
            throw new StationNotFoundException(String.format("Station %s was not found.", name));
        }

        return station.get();    }

    @Override
    public List<Station> getStationsByHdEnabled(Boolean enabled) {
        return stationRepository.findByHdEnabledOrderByCallSign(enabled);
    }

    @Override
    public void deleteById(long id) {
        stationRepository.deleteById(id);
    }

    @Override
    public Station createOrUpdate(Station station) {
        return stationRepository.save(station);
    }
}
