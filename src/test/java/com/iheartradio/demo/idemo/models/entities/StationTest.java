package com.iheartradio.demo.idemo.models.entities;

import org.hibernate.validator.HibernateValidator;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.validation.ConstraintViolation;

import java.util.Set;

import static org.junit.Assert.*;

public class StationTest {

    @Autowired
    private LocalValidatorFactoryBean validator;

    @Before
    public void setUp() throws Exception {
        validator = new LocalValidatorFactoryBean();
        validator.setProviderClass(HibernateValidator.class);
        validator.afterPropertiesSet();
    }

    @Test
    public void station_happyPath_test() {
        // given
        Station station = new Station();
        // when
        station.setName("Home Town rock"); station.setHdEnabled(true); station.setCallSign("kann");  station.setStationId(0L);
        // then
        validate(station, 0);
    }

    @Test
    public void station_nullName_test() {
        // given
        Station station = new Station();
        // when
        station.setName(null); station.setHdEnabled(true); station.setCallSign("kann");  station.setStationId(0L);
        // then
        validate(station, 1);
    }

    @Test
    public void station_blankName_test() {
        // given
        Station station = new Station();
        // when
        station.setName(""); station.setHdEnabled(true); station.setCallSign("kann");  station.setStationId(0L);
        // then
        validate(station, 1);
    }

    private void validate(Station station, int numberViolations) {
        Set<ConstraintViolation<Station>> constraintViolations = validator.validate(station);

        assertTrue("Expected validation error not found", constraintViolations.size() == numberViolations);
    }
}