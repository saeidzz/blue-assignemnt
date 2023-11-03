package com.blubank.doctorappointment.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * A TimeSheet.
 */
@Entity
@Table(name = "tbl_time_sheet")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TimeSheet implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "time_sheet_date")
    private LocalDate date;

    @Column(name = "from_time")
    private LocalDateTime fromTime;

    @Column(name = "to_time")
    private LocalDateTime toTime;

    @ManyToOne
    private Doctor doctor;

    @ManyToOne
    private Patient patient;

}
