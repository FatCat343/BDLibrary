package com.bdcourse.library.storage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StorageService {
    @Autowired
    private StorageRepository storageRepository;

    public List<Storage> findAll(){
        return storageRepository.findAll();
    }
}
