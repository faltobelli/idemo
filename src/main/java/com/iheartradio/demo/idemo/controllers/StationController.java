package com.iheartradio.demo.idemo.controllers;

import com.iheartradio.demo.idemo.models.entities.Station;
import com.iheartradio.demo.idemo.services.face.StationService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1", produces = MediaType.APPLICATION_JSON_VALUE)
public class StationController {

    private StationService stationService;

    public StationController(StationService stationService) {
        this.stationService = stationService;
    }

    @GetMapping("/stations")
    public List<Station> retrieveAllStations() {
        return stationService.getAllStations();
    }

    @GetMapping("/station/id/{id}")
    public Station retrieveStationById(@PathVariable Long id) {
        return stationService.getStationById(id);
    }

    @GetMapping("/station/name/{name}")
    public Station retrieveStationByName(@PathVariable String name) {
        return stationService.getStationByName(name);
    }

    @GetMapping("/stations/hdenabled/{hdEnabled}")
    public List<Station> retrieveStationByHdEnabled(@PathVariable boolean hdEnabled) {
        return stationService.getStationsByHdEnabled(hdEnabled);
    }

    @DeleteMapping("/station/{id}")
    public void deleteById(@PathVariable long id) {
        stationService.deleteById(id);
    }

    @PostMapping("/station")
    public Boolean createOrUpdateStation(@Valid @RequestBody Station station) {
        return stationService.createOrUpdate(station) != null ? true : false;
    }


}
