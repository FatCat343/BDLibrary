package com.bdcourse.library.edition.outdoorEdition;

import com.bdcourse.library.edition.Edition;
import com.bdcourse.library.edition.indoorEdition.IndoorEdition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OutdoorEditionService {
    @Autowired
    OutdoorEditionRepository outdoorEditionRepository;

    public OutdoorEdition save(OutdoorEdition edition) {
        return outdoorEditionRepository.save(edition);
    }

    public void delete(OutdoorEdition edition) {
        outdoorEditionRepository.delete(edition);
    }

    public OutdoorEdition find(Edition edition) {
        //long counter = 0;
        Optional<OutdoorEdition> result = outdoorEditionRepository.findById(edition.getId());
        return result.orElse(null);
//        return outdoorEditionRepository.findById(edition.getEdition_id()).map(
//                f -> f.getRentalPeriod()
//        ).orElse(null);
    }

    public OutdoorEdition findFetch(Edition edition) {
        return outdoorEditionRepository.findOutdoorEditionByIdFetchAll(edition.getId());
    }
}
