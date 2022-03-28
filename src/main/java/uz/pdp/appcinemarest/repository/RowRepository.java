package uz.pdp.appcinemarest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.pdp.appcinemarest.entity.Actor;
import uz.pdp.appcinemarest.entity.Row;
import uz.pdp.appcinemarest.projection.CustomRow;
import uz.pdp.appcinemarest.projection.RowsProjection;

import java.util.List;
import java.util.UUID;

// Zuhridin Bakhriddinov 3/15/2022 12:50 AM
public interface RowRepository extends JpaRepository<Row,Integer> {

    @Query(value = "SELECT r.id,\n" +
            "       r.number,\n" +
            "       h.name                as hall\n" +
            "FROM row r\n" +
            "         join hall h on r.hall_id = h.id\n" +
            "where h.id =:hallId", nativeQuery = true)
    List<CustomRow> getRowsByHallId(Integer hallId);

    @Query(value = "select distinct r.id as rowId ,r.number as rowNumber\n" +
            "from row r\n" +
            "join seat s on r.id = s.row_id\n" +
            "join hall h on h.id = r.hall_id\n" +
            "join movie_session ms on h.id = ms.hall_id\n" +
            "where ms.id = :movieSessionId",nativeQuery = true)
    List<RowsProjection> getAvailableRowsByMovieAnnouncementId(Integer movieSessionId);


}
