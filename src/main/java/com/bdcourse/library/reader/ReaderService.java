package com.bdcourse.library.reader;

import com.bdcourse.library.edition.Edition;
import com.bdcourse.library.publication.Publication;
import com.bdcourse.library.reader.student.Student;
import com.vaadin.flow.templatemodel.AllowClientUpdates;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReaderService {
    @Autowired
    private ReaderRepository readerRepository;

    public List<Reader> findAll() {
        return (List<Reader>) readerRepository.findAll();
    }

    public Reader save(Reader reader) {
        return readerRepository.save(reader);
    }

    public void delete(Reader reader) {
        readerRepository.delete(reader);
    }

//    public List<Student> findAll() {
//        return
//    }
    public List<Reader> findReaderByPublication(String name) {
        //System.out.println(name);
        //System.out.println(readerRepository.findReaderByPublication(name));

        return readerRepository.findReaderByPublication(name);
    }
}
