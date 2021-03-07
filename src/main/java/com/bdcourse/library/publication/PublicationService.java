package com.bdcourse.library.publication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PublicationService {
    @Autowired
    PublicationRepository publicationRepository;

    public List<Object[]> findPublicationsByPopularity() {
        return publicationRepository.findPublicationsByPopularity();
    }
}
