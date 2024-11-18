package com.baipiao.api.stat.dto;
import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor

public class OrganizerUserStat {
    private Long  totalEvents;

    private Long  totalAttendance;
    private Long  minAttendance;
    private Long  maxAttendance;
    private BigDecimal averageAttendance;

    private Long  totalCapacity;
    private Long  minCapacity;
    private Long  maxCapacity;
    private BigDecimal averageCapacity;

}
