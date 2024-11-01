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
}
