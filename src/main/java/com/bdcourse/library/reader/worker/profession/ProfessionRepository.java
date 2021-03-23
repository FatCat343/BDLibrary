package com.bdcourse.library.reader.worker.profession;

import org.springframework.data.repository.CrudRepository;

public interface ProfessionRepository extends CrudRepository<Profession, Integer> {
    boolean existsProfessionByName(String name);
}
