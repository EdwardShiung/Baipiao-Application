package com.baipiao.api.stat.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DailyStat implements Serializable{
    private String date;

    private Long count;
}
