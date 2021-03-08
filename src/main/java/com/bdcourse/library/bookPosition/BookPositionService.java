package com.bdcourse.library.bookPosition;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookPositionService {
    @Autowired
    private BookPositionRepository bookPositionRepository;


}
