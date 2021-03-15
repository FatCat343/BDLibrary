package com.bdcourse.library.edition.indoorEdition;

import com.bdcourse.library.edition.Edition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IndoorEditionService {
    @Autowired
    IndoorEditionRepository indoorEditionRepository;

    public IndoorEdition save(IndoorEdition edition) {
        return indoorEditionRepository.save(edition);
    }

    public void delete(IndoorEdition edition) {
        indoorEditionRepository.delete(edition);
    }

    public IndoorEdition find(Edition edition) {
        return indoorEditionRepository.findById(edition.getEdition_id()).orElse(null);
    }

    public IndoorEdition findFetch(Edition edition) {
        return indoorEditionRepository.findIndoorEditionByIdFetchAll(edition.getEdition_id());
    }
}
