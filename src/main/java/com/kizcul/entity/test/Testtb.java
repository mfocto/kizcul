package com.kizcul.entity.test;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Table(name="testtb")
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Testtb {

    @Id
    private String col1;
    @Column
    private Long col2;
    @Column
    private LocalDate col3;
}
