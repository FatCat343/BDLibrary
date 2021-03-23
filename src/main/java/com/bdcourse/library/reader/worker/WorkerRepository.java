package com.bdcourse.library.reader.worker;

import com.bdcourse.library.reader.worker.company.Company;
import com.bdcourse.library.reader.worker.profession.Profession;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.math.BigInteger;

public interface WorkerRepository extends CrudRepository<Worker, Integer> {

    @Query(value = "SELECT COUNT(w) FROM Worker w WHERE w.id <> :id AND w.firstName = :firstname" +
            " AND w.lastName = :lastname AND w.company = :company AND w.profession = :profession")
    BigInteger existsWorkerByCompanyAndProfessionAndFirstNameAndFirstNameAndLibrary(@Param("company") Company company,
                                                                                    @Param("profession") Profession profession,
                                                                                    @Param("firstname") String firstName,
                                                                                    @Param("lastname") String lastName,
                                                                                    @Param("id") Integer id);
}
