package com.bdcourse.library.edition.indoorEdition;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IndoorEditionService {
    @Autowired
    IndoorEditionRepository indoorEditionRepository;
}
