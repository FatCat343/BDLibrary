package com.bdcourse.library.storage;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface StorageRepository extends CrudRepository<Storage, Integer> {

    @Override
    @Query(value = "SELECT * FROM library_schema.storage s WHERE s.storage_id != 0", nativeQuery = true)
    List<Storage> findAll();
}
