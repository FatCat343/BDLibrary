package com.bdcourse.library.publication.book.category;

import com.bdcourse.library.publication.book.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {
    @Autowired
    private BookRepository bookRepository;


}
