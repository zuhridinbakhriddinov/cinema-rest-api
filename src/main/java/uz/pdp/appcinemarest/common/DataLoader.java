package uz.pdp.appcinemarest.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import uz.pdp.appcinemarest.entity.Hall;
import uz.pdp.appcinemarest.entity.Row;
import uz.pdp.appcinemarest.repository.HallRepository;
import uz.pdp.appcinemarest.repository.RowRepository;

import java.util.ArrayList;

// Zuhridin Bakhriddinov 3/15/2022 11:54 AM
@Component
public class DataLoader implements CommandLineRunner {

    @Value("${spring.sql.init.mode}")
    String initMode;


    @Autowired
    RowRepository rowRepository;
    @Autowired
    HallRepository hallRepository;

    @Override
    public void run(String... args) throws Exception {
        if (initMode.equals("always")) {
            Hall hall=new Hall();
            hall.setName("Zal1");
            ArrayList<Row> rowList = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                rowList.add(new Row(i+1,hall));
            }

           hall.setRowList(rowList);

            hallRepository.save(hall);

        }



    }
}
