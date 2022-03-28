package uz.pdp.appcinemarest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.appcinemarest.entity.Distributor;
import uz.pdp.appcinemarest.mapper.DistributorMapper;
import uz.pdp.appcinemarest.payload.DistributorDto;
import uz.pdp.appcinemarest.repository.DistributorRepository;

import java.util.List;
import java.util.Optional;

// Zuhridin Bakhriddinov 3/14/2022 8:19 PM
@Service
public class DistributorService {
    @Autowired
    DistributorRepository distributorRepository;

    @Autowired
    DistributorMapper distributorMapper;

    public Distributor addDistributor(DistributorDto distributorDto) {


        if (distributorDto != null) {
/*            Distributor distributor = new Distributor();
            distributor.setName(distributorDto.getName());
            distributor.setDescription(distributorDto.getDescription());*/
            return distributorRepository.save(distributorMapper.toDistributor(distributorDto));

        } else
            return null;

    }

    public boolean deleteDistributor(int id) {
        List<Distributor> distributorList = distributorRepository.findAll();


        for (Distributor distributor : distributorList) {
            if (!distributor.getId().equals(id)) {
                return false;
            }
        }
        distributorRepository.deleteById(id);
        return true;
    }

    public List<Distributor> getDistributorList() {
        return distributorRepository.findAll();
    }


    public boolean editDistributor(int id, DistributorDto distributorDto) {
        Optional<Distributor> optionalDistributor = distributorRepository.findById(id);
        if (!optionalDistributor.isPresent()) {
            return false;
        }
        Distributor distributor = optionalDistributor.get();
        distributor.setName(distributorDto.getName());
        distributor.setDescription(distributorDto.getDescription());
        distributorRepository.save(distributor);
        return true;
    }


}
