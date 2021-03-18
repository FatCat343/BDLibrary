package com.bdcourse.library.edition;

import com.bdcourse.library.publication.Publication;
import com.bdcourse.library.publication.author.Author;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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

    public List<Edition> findEditionByReaderInAssignedLibrary(String firstName, String lastName, LocalDate start, LocalDate finish) {
        return editionRepository.findEditionByReaderInAssignedLibrary(firstName, lastName, start, finish);
    }

    public List<Edition> findEditionByReaderInNotAssignedLibrary(String firstName, String lastName, LocalDate start, LocalDate finish) {
        return editionRepository.findEditionByReaderInNotAssignedLibrary(firstName, lastName, start, finish);
    }
}
