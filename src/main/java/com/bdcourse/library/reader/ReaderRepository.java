package com.bdcourse.library.reader;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReaderRepository extends CrudRepository<Reader, Integer> {
    //2 query
    @Query(value = "SELECT \n" +
            "    * ,0 AS clazz_\n" +
            "FROM \n" +
            "    library_schema.reader r\n" +
            "WHERE \n" +
            "    r.reader_id IN (SELECT\n" +
            "                        d.reader_id\n" +
            "                    FROM \n" +
            "                        library_schema.distribution d \n" +
            "                        JOIN library_schema.edition e ON d.edition_id = e.edition_id\n" +
            "                        JOIN library_schema.publication p ON p.publication_id = e.publication_id  \n" +
            "                    WHERE \n" +
            "                        p.title = :name\n" +
            "                        AND d.date_return IS NULL) ", nativeQuery = true)
    List<Reader> findReaderByPublication(@Param("name") String name);

//    //3 query
//
//    List<Reader> findReaderByEdition(@Param("code") int code);
//
//    //4 query
//
//    List<Object[]> findReaderAndPublicationByEditionAndDate();
//
//    //8 query
//    List<Reader> findReaderByStaffAndDate();
//
//    //10 query
//    List<Reader> findReaderExpired();
//
//    //13 query
//    List<Reader> findReaderByAvoidingLibraryAndDate();
}
