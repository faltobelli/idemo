package com.iheartradio.demo.idemo.repositories.face;

import com.iheartradio.demo.idemo.models.entities.Station;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface StationRepository extends CrudRepository<Station, Long> {
    List<Station> findByHdEnabled(Boolean enabled);
    List<Station> findByHdEnabledOrderByCallSign(Boolean enabled);
    Optional<Station> findByName(String name);
}
