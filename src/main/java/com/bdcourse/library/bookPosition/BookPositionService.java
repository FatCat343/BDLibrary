package com.bdcourse.library.bookPosition;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookPositionService {
    @Autowired
    private BookPositionRepository bookPositionRepository;

    public List<BookPosition> findAll() {
        return (List<BookPosition>) bookPositionRepository.findAll();
    }

    public BookPosition save(BookPosition bookPosition) {
        return bookPositionRepository.save(bookPosition);
    }

    public boolean exists(BookPosition bookPosition) {
        return bookPositionRepository.existsBookPositionByStorageAndRackNumberAndShelfNumber(bookPosition.getStorage(),
                bookPosition.getRackNumber(), bookPosition.getShelfNumber());
    }

    public void delete(BookPosition bookPosition) {
        bookPositionRepository.delete(bookPosition);
    }

    public List<BookPosition> findAllFetch() {
        return bookPositionRepository.findAllFetch();
    }
}
