package com.bdcourse.library.reader;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ReaderRepository extends CrudRepository<Reader, Integer> {

    @Override
    @Query(value = "SELECT *, 0 AS clazz_ FROM library_schema.reader r WHERE r.reader_id != 0 ORDER BY r.reader_id ASC", nativeQuery = true)
    Iterable<Reader> findAll();

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

    //3 query
    @Query(value = "SELECT \n" +
            "    * , 0 AS clazz_ \n" +
            "FROM \n" +
            "    library_schema.reader r\n" +
            "WHERE \n" +
            "    r.reader_id IN (SELECT\n" +
            "                        d.reader_id\n" +
            "                    FROM \n" +
            "                        library_schema.distribution d \n" +
            "                        JOIN library_schema.edition e ON d.edition_id = e.edition_id\n" +
            "                    WHERE \n" +
            "                        e.edition_code = :code\n" +
            "                        AND d.date_return IS NULL)", nativeQuery = true)
    List<Reader> findReaderByEdition(@Param("code") int code);

    //4 query
    @Query(value = "SELECT\n" +
            "    r.firstname, r.lastname, e.edition_code\n" +
            "FROM \n" +
            "    library_schema.distribution d\n" +
            "    JOIN library_schema.reader r ON r.reader_id = d.reader_id \n" +
            "    JOIN library_schema.edition e ON d.edition_id = e.edition_id\n" +
            "    JOIN library_schema.publication p ON p.publication_id = e.publication_id  \n" +
            "WHERE \n" +
            "    p.title = :title\n" +
            "    AND d.date_give BETWEEN :start AND :finish ", nativeQuery = true)
    List<Object[]> findReaderAndEditionByPublicationAndDate(@Param("title") String title,
                                                        @Param("start")LocalDate start,
                                                        @Param("finish") LocalDate finish);

    //8 query
    @Query(value = "SELECT \n" +
            "    r.*, 0 AS  clazz_\n" +
            "FROM \n" +
            "    library_schema.reader r \n" +
            "    JOIN library_schema.distribution d ON d.reader_id = r.reader_id\n" +
            "    JOIN library_schema.staff s ON s.staff_id = d.staff_id\n" +
            "WHERE \n" +
            "    s.staff_id = :id\n" +
            "    AND d.date_give BETWEEN :start AND :finish", nativeQuery = true)
    List<Reader> findReaderByStaffAndDate(@Param("id") Integer staffId, @Param("start") LocalDate start,
                                          @Param("finish") LocalDate finish);

    //10 query
    @Query(value = "SELECT \n" +
            "    r.* , 0 AS clazz_\n" +
            "FROM \n" +
            "    library_schema.reader r \n" +
            "    JOIN library_schema.distribution d ON d.reader_id = r.reader_id \n" +
            "    JOIN library_schema.edition e ON d.edition_id = e.edition_id \n" +
            "    JOIN library_schema.outdoor o ON o.edition_id = e.edition_id \n" +
            "WHERE \n" +
            "    d.date_give + o.rental_period < NOW ()", nativeQuery = true)
    //@Query(value = "SELECT *, 0 AS clazz_ FROM library_schema.some_function()", nativeQuery = true)
    List<Reader> findReaderExpired();

    //13 query
    @Query(value = "SELECT \n" +
            "    r.*, 0 AS clazz_\n" +
            "FROM \n" +
            "    library_schema.reader r \n" +
            "WHERE \n" +
            "    r.reader_id > 0 AND " +
            "    r.reader_id NOT IN (SELECT \n" +
            "                d.reader_id\n" +
            "            FROM \n" +
            "                library_schema.distribution d\n" +
            "            WHERE \n" +
            "                d.date_give BETWEEN :start AND :finish \n" +
            "                OR d.date_return BETWEEN :start AND :finish)", nativeQuery = true)
    List<Reader> findReaderByAvoidingLibraryAndDate(@Param("start") LocalDate start, @Param("finish") LocalDate finish);
}
