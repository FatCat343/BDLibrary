package com.bdcourse.library.edition;

import com.bdcourse.library.publication.Publication;
import com.bdcourse.library.publication.author.Author;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EditionService {
    @Autowired
    private EditionRepository editionRepository;

    public List<Edition> findAll() {
        return (List<Edition>) editionRepository.findAll();
    }

    public void delete(Edition edition) {
        editionRepository.delete(edition);
    }

    public Edition save(Edition edition) {
        return editionRepository.save(edition);
    }

    public List<Edition> findAllFetchAll() {
        return editionRepository.findAllFetchAll();
    }

    public List<Edition> findEditionByAuthor(Author author) {
        return author == null ? new ArrayList<Edition>() : editionRepository.findEditionByAuthor(author.getId());
    }

    public List<Edition> findEditionByPublication(Publication publication) {
        return publication == null ? new ArrayList<Edition>() : editionRepository.findEditionByPublication(publication.getId());
    }
}
