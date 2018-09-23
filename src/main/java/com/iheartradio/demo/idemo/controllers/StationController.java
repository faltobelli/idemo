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

    @GetMapping(value = "/stations/hello")
    public String hello() {
        return "Hello listener...";
    }


    @GetMapping("/stations")
    public List<Station> retrieveAllStations() {
        return (List<Station>) stationService.getAllStations();
    }

    @GetMapping("/stations/id/{id}")
    public Station retrieveStationById(@PathVariable long id) {
        return stationService.getStationById(id);
    }

    @GetMapping("/stations/name/{name}")
    public Station retrieveStationByName(@PathVariable String name) {
        return stationService.getStationByName(name);
    }

    @GetMapping("/stations/hdenabled/{hdEnabled}")
    public List<Station> retrieveStationByName(@PathVariable boolean hdEnabled) {
        return stationService.getStationsByHdEnabled(hdEnabled);
    }


    @DeleteMapping("/stations/{id}")
    public void deleteById(@PathVariable long id) {
        stationService.deleteById(id);
    }

    @PostMapping("/stations")
    public void createOrUpdateStation(@Valid @RequestBody Station station) {
        Station savedStation = stationService.createOrUpdate(station);
    }


}
