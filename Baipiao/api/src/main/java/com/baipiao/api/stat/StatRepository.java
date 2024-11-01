package com.baipiao.api.stat;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import com.baipiao.api.stat.dto.*;
@Repository
public class StatRepository {

    @PersistenceContext
    private EntityManager entityManager;
    // Read: Retrieve all categories
    List<CategoryStat> getCategoryStat(){
        String sql = "SELECT C.id as id, C.name as name, COUNT(*) as count FROM events E, categories C WHERE E.category_id = C.id GROUP BY C.id";
        Query query = entityManager.createNativeQuery(sql, CategoryStat.class);
        return query.getResultList();
    }


    List<OrganizerStat> getOrganizerStat(){
        String sql = "SELECT O.id as id, O.name as name, COUNT(*) as count FROM events E, organization O WHERE E.category_id = O.id GROUP BY O.id";
        Query query = entityManager.createNativeQuery(sql, OrganizerStat.class);
        return query.getResultList();
    }

    List<DailyStat> getDailyStat(){
        String sql = "SELECT date_of_month as date, count(*) as count FROM ( SELECT *, TO_CHAR(start_date, 'DD') AS date_of_month FROM events e ) GROUP BY date_of_month ORDER BY date_of_month";
        Query query = entityManager.createNativeQuery(sql, DailyStat.class);
        return query.getResultList();
    }


}