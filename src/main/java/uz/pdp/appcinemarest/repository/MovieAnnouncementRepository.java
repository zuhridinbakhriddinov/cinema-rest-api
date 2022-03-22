package uz.pdp.appcinemarest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.pdp.appcinemarest.entity.MovieAnnouncement;
import uz.pdp.appcinemarest.projection.CustomMovieAnnouncement;

import java.util.List;

public interface MovieAnnouncementRepository extends JpaRepository<MovieAnnouncement, Integer> {





}
