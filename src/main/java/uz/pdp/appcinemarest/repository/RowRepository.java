package uz.pdp.appcinemarest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.pdp.appcinemarest.entity.Actor;
import uz.pdp.appcinemarest.entity.Row;
import uz.pdp.appcinemarest.projection.CustomRow;

import java.util.List;
import java.util.UUID;

// Zuhridin Bakhriddinov 3/15/2022 12:50 AM
public interface RowRepository extends JpaRepository<Row,Integer> {

    @Query(value = "SELECT " +
            " cast(r.id as varchar) as id, r.number, h.name as hallName FROM rows r " +
            "join halls h on r.hall_id = h.id\n" +
            "where h.id = :hallId", nativeQuery = true)
    List<CustomRow> getRowsByHallId(Integer hallId);


}
