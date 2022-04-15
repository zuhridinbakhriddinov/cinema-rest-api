package uz.pdp.appcinemarest.service;

import com.twilio.exception.RestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.appcinemarest.entity.Hall;
import uz.pdp.appcinemarest.entity.MovieAnnouncement;
import uz.pdp.appcinemarest.entity.Row;
import uz.pdp.appcinemarest.mapper.RowMapper;
import uz.pdp.appcinemarest.payload.ApiResponse;
import uz.pdp.appcinemarest.payload.MovieAnnouncementDto;
import uz.pdp.appcinemarest.payload.RowDto;
import uz.pdp.appcinemarest.projection.CustomRow;
import uz.pdp.appcinemarest.repository.HallRepository;
import uz.pdp.appcinemarest.repository.RowRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

// Zuhridin Bakhriddinov 3/15/2022 2:46 PM
@Service
public class RowService {

    @Autowired
    RowRepository rowRepository;
    @Autowired
    HallRepository hallRepository;
    @Autowired
    RowMapper rowMapper;



    public List<CustomRow> getRow(int id){
     return   rowRepository.getRowsByHallId(id);
    }

  /*  public HttpEntity<?> updateRow(int id, RowDto rowDto) {
        Row row = rowRepository.findById(id).orElseThrow(RuntimeException::new);
        Hall hall = hallRepository.findById(rowDto.getHallId()).orElseThrow(RuntimeException::new);
        Row row1 = new Row(row.getNumber(), hall);
        RowDto dto = rowMapper.toRowDto(row1);
        return new ResponseEntity<>(new ApiResponse("success", true, dto), HttpStatus.OK);

    }*/



}
