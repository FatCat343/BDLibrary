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

    public Storage save(Storage storage) {
        return storageRepository.save(storage);
    }

    public void delete(Storage storage) {
        storageRepository.delete(storage);
    }
}
