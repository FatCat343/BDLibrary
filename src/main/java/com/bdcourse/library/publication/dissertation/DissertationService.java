package com.bdcourse.library.publication.dissertation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DissertationService {
    @Autowired
    private DissertationRepository dissertationRepository;
}
