package uz.pdp.appcinemarest.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.appcinemarest.entity.*;

import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import java.util.List;

// Zuhridin Bakhriddinov 3/16/2022 11:32 AM
@AllArgsConstructor
@NoArgsConstructor
@Data
public class MovieDto {

    private String title;
    private String description;
    private int durationInMinutes;
    private MultipartFile coverImage;

    private MultipartFile trailerVideo;

    private List<Integer> directorIds;

    private List<Integer> genreIds;

    private double minPrice;

    private int distributorId;

    private List<Integer> actorIds;


    private double distributorShareInPercent;


}
/*
* acct_1KhfDrGNKbQ4R3wK
* zest-remedy-feisty-homage*/
