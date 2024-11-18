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
        Query query = this.entityManager.createNativeQuery(sql, CategoryStat.class);
        return query.getResultList();
    }


    List<OrganizerStat> getOrganizerStat(){
        String sql = "SELECT O.id as id, O.name as name, COUNT(*) as count FROM events E, organization O WHERE E.category_id = O.id GROUP BY O.id";
        Query query = this.entityManager.createNativeQuery(sql, OrganizerStat.class);
        return query.getResultList();
    }

    List<DailyStat> getDailyStat(){
        String sql = "SELECT date_of_month as date, count(*) as count FROM ( SELECT *, TO_CHAR(start_date, 'DD') AS date_of_month FROM events e ) GROUP BY date_of_month ORDER BY date_of_month";
        Query query = this.entityManager.createNativeQuery(sql, DailyStat.class);
        return query.getResultList();
    }

    Integer getUserCount() {
        String sql = "SELECT COUNT(*) FROM users";
        Query query = this.entityManager.createNativeQuery(sql);
        Object result = query.getSingleResult();
        return result != null ? ((Number) result).intValue() : 0;
    }

    Integer getEventsCount() {
        String sql = "SELECT COUNT(*) FROM events";
        Query query = this.entityManager.createNativeQuery(sql);
        Object result = query.getSingleResult();
        return result != null ? ((Number) result).intValue() : 0;
    }

    Double getAvgEventCapacity() {
        String sql = "SELECT AVG(capacity) FROM events";
        Query query = this.entityManager.createNativeQuery(sql);
        Object result = query.getSingleResult();
        return result != null ? ((Number) result).doubleValue() : 0;
    }
    Integer getMinEventCapacity() {
        String sql = "SELECT MIN(capacity) FROM events";
        Query query = this.entityManager.createNativeQuery(sql);
        Object result = query.getSingleResult();
        return result != null ? ((Number) result).intValue() : 0;
    }
    Integer getMaxEventCapacity() {
        String sql = "SELECT MAX(capacity) FROM events";
        Query query = this.entityManager.createNativeQuery(sql);
        Object result = query.getSingleResult();
        return result != null ? ((Number) result).intValue() : 0;
    }

    Integer getOrganizationsCount() {
        String sql = "SELECT COUNT(*) FROM organizations";
        Query query = this.entityManager.createNativeQuery(sql);
        Object result = query.getSingleResult();
        return result != null ? ((Number) result).intValue() : 0;
    }
    Integer getVenuesCount() {
        String sql = "SELECT COUNT(*) FROM venues";
        Query query = this.entityManager.createNativeQuery(sql);
        Object result = query.getSingleResult();
        return result != null ? ((Number) result).intValue() : 0;
    }

    Integer getEventsCountToday() {
        String sql = "SELECT COUNT(*) AS today_events FROM public.events WHERE start_date::date = CURRENT_DATE ";
        Query query = this.entityManager.createNativeQuery(sql);
        Object result = query.getSingleResult();
        return result != null ? ((Number) result).intValue() : 0;
    }
    Integer getEventsCountThisWeek() {
        String sql = "SELECT COUNT(*) AS this_week_events FROM public.events WHERE start_date >= CURRENT_DATE AND start_date < CURRENT_DATE + INTERVAL '7 days'";
        Query query = this.entityManager.createNativeQuery(sql);
        Object result = query.getSingleResult();
        return result != null ? ((Number) result).intValue() : 0;
    }
    Integer getEventsCountNextWeek() {
        String sql = "SELECT COUNT(*) AS next_week_events FROM public.events WHERE start_date >= CURRENT_DATE + INTERVAL '7 days' AND start_date < CURRENT_DATE + INTERVAL '14 days'";
        Query query = this.entityManager.createNativeQuery(sql);
        Object result = query.getSingleResult();
        return result != null ? ((Number) result).intValue() : 0;
    }

}