package com.iheartradio.demo.idemo.models.entities;

import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
public class Station {
    @Id
    @GeneratedValue
    private Long stationId;

    @Column(nullable = false)
    private String name;
    @ColumnDefault(value = "false")
    private boolean hdEnabled;
    @Column(nullable = false)
    private String callSign;

}
