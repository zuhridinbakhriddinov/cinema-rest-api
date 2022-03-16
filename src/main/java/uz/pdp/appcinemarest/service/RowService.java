package uz.pdp.appcinemarest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.appcinemarest.entity.Row;
import uz.pdp.appcinemarest.projection.CustomRow;
import uz.pdp.appcinemarest.repository.RowRepository;

import java.util.List;

// Zuhridin Bakhriddinov 3/15/2022 2:46 PM
@Service
public class RowService {

    @Autowired
    RowRepository rowRepository;

    public List<CustomRow> getRow(int id){
     return   rowRepository.getRowsByHallId(id);


    }
}
