package com.bdcourse.library.publication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

@Service
public class PublicationService {
    @Autowired
    private PublicationRepository publicationRepository;

    public List<Publication> findAll() {
        return (List<Publication>) publicationRepository.findAll();
    }

    public boolean exist(Publication publication){
        return !publicationRepository.existsPublicationByAuthorAndTitle(publication.getAuthor(),
                publication.getTitle(), publication.getId()).equals(BigInteger.ZERO);
    }




    public List<Object[]> findPublicationsByPopularity() {
        return publicationRepository.findPublicationsByPopularity();
    }
}
