package com.baipiao.api.stat.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor

public class OrganizerStat implements Serializable {
    @JsonIgnore
    private Long id;

    private String name;

    private Long count;

}