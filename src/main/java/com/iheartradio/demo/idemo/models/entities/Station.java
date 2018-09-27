package com.iheartradio.demo.idemo.models.entities;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Entity
@Data
public class Station implements Serializable {
    @Id
    @GeneratedValue
    private Long stationId;

    @NotBlank
    @Column(nullable = false)
    private String name;
    @ColumnDefault(value = "false")
    private boolean hdEnabled;
    @Column(nullable = false)
    private String callSign;

}
