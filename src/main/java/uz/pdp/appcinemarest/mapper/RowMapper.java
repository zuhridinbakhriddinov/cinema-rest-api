package uz.pdp.appcinemarest.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;
import uz.pdp.appcinemarest.entity.Row;
import uz.pdp.appcinemarest.payload.RowDto;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = "spring")
@Component
public interface RowMapper {

   /* @Mapping(source = "hall.id", target = "hallId")
    RowDto toRowDto(Row row);*/
}
