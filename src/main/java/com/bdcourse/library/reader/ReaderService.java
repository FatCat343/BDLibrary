package com.bdcourse.library.reader;

import com.vaadin.flow.templatemodel.AllowClientUpdates;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReaderService {
    @Autowired
    private ReaderRepository readerRepository;

}
