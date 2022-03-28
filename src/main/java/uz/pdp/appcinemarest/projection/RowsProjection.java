package uz.pdp.appcinemarest.projection;

import org.springframework.beans.factory.annotation.Value;

import java.util.List;

public interface RowsProjection {
    Integer getRowId();
    Integer getRowNumber();

    @Value("#{@seatRepository.getAvailableSeatByRowIdAndMovieSessionId(target.rowId,target.movieSessionId)}")
    List<SeatsProjection> getSeats();

}
