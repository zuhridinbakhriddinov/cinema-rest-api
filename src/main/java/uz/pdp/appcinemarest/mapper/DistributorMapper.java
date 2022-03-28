package uz.pdp.appcinemarest.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;
import uz.pdp.appcinemarest.entity.Distributor;
import uz.pdp.appcinemarest.payload.DistributorDto;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
@Component
public interface DistributorMapper {
    Distributor toDistributor(DistributorDto distributorDto);

}
