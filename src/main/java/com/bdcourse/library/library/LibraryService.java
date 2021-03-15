package com.bdcourse.library.library;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LibraryService {
    @Autowired
    private LibraryRepository libraryRepository;

    public List<Library> findAll() {
        return (List<Library>) libraryRepository.findAll();
    }

    public void delete(Library library) {
        libraryRepository.delete(library);
    }

    public Library save(Library library) {
        System.out.println("save library = " + library);
        Library result =  libraryRepository.save(library);
        System.out.println("result = " + result);
        return result;
    }
}
