package com.baipiao.api.stat;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baipiao.api.stat.dto.CategoryStat;
import com.baipiao.api.stat.dto.DailyStat;
import com.baipiao.api.stat.dto.OrganizerStat;
@Service
public class StatService {
    @Autowired
    private StatRepository statRepository;
    
    public List<CategoryStat> getCategoryStat() {
            return statRepository.getCategoryStat();
    }

    public List<OrganizerStat> getOrganizerStat() {
        return statRepository.getOrganizerStat();
    }

    public List<DailyStat> getDailyStat() {
        return statRepository.getDailyStat();
    }
    public Integer getUserCount() {
        return statRepository.getUserCount();
    }
    public Integer getEventsCount() {
        return statRepository.getEventsCount();
    }
    public Double getAvgEventCapacity() {
        return statRepository.getAvgEventCapacity();
    }
    public Integer getMinEventCapacity() {
        return statRepository.getMinEventCapacity();
    }
    public Integer getMaxEventCapacity() {
        return statRepository.getMaxEventCapacity();
    }
    public Integer getOrganizationsCount() {
        return statRepository.getOrganizationsCount();
    }

    public Integer getVenuesCount() {
        return statRepository.getVenuesCount();
    }

    public Integer getEventsCountToday() {
        return statRepository.getEventsCountToday();
    }
    public Integer getEventsCountThisWeek() {
        return statRepository.getEventsCountThisWeek();
    }
    public Integer getEventsCountNextWeek() {
        return statRepository.getEventsCountNextWeek();
    }
}
