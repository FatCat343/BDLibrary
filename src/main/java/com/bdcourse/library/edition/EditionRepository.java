package com.bdcourse.library.edition;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;


import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;

public interface EditionRepository extends CrudRepository<Edition, Integer> {

    @Override
    @Query(value = "SELECT *, 0 AS clazz_ FROM library_schema.edition e WHERE e.edition_id != 0 " +
            "ORDER BY e.edition_id ASC", nativeQuery = true)
    Iterable<Edition> findAll();

    @Query(value = "SELECT e FROM Edition e JOIN FETCH e.publication publ JOIN FETCH e.position pos " +
            "JOIN FETCH pos.storage stor WHERE e.id > 0 ORDER BY e.id ASC")
    List<Edition> findAllFetchAll();

    @Query(value = "SELECT COUNT(e) FROM Edition e WHERE e.id <> :id AND e.code = :code")
    BigInteger existsEditionByCode(@Param("code") Integer code, @Param("id") Integer id);

    //11 Query
    //    @Query(value = "SELECT e \n" +
//            "FROM Edition e \n" +
//            "JOIN FETCH e.publication publ \n" +
//            "JOIN FETCH e.position pos \n" +
//            "JOIN FETCH pos.storage stor \n" +
//            "WHERE e.edition_id > 0 \n" +
//            "AND e.dateArrived BETWEEN :start AND :finish \n" +
//            "ORDER BY e.edition_id ASC")
//    List<Edition> findAllByDateArrivedBetweenFetchAll(@Param("start") LocalDate start,
//                                                      @Param("finish") LocalDate finish);
//
//    @Query(value = "SELECT e \n" +
//            "FROM Edition e \n" +
//            "JOIN FETCH e.publication publ \n" +
//            "JOIN FETCH e.position pos \n" +
//            "JOIN FETCH pos.storage stor \n" +
//            "WHERE e.edition_id > 0 \n" +
//            "AND e.dateLeft BETWEEN :start AND :finish \n" +
//            "ORDER BY e.edition_id ASC")
//    List<Edition> findAllByDateLeftBetweenFetchAll(@Param("start") LocalDate start,
//                                                      @Param("finish") LocalDate finish);


//    @Query(value = "SELECT d FROM Distribution d JOIN FETCH d.edition e WHERE d.id = :id")
//    Edition findEditionByIdFetchPublication(@Param("id") Integer id);

//    //5 query
//    @Query(value = "SELECT \n" +
//            "    e.*, 0 AS clazz_ \n" +
//            "FROM\n" +
//            "    library_schema.edition e\n" +
//            "    JOIN library_schema.distribution d ON d.edition_id = e.edition_id\n" +
//            "    JOIN library_schema.reader r ON r.reader_id = d.reader_id\n" +
//            "    JOIN library_schema.bookposition bp ON bp.position_id = e.position_id\n" +
//            "    JOIN library_schema.storage s ON s.storage_id = bp.storage_id\n" +
//            "WHERE \n" +
//            "    r.reader_id = :id \n" +
//            "    AND d.date_give BETWEEN :start AND :finish \n" +
//            "    AND r.library_id = s.library_id", nativeQuery = true)
//    List<Edition> findEditionByReaderInAssignedLibrary(@Param("id") Integer readerId,
//                                                       @Param("start") LocalDate start,
//                                                       @Param("finish") LocalDate finish);
//    //6 query
//    @Query(value = "SELECT \n" +
//        "    e.*, 0 AS clazz_ \n" +
//        "FROM\n" +
//        "    library_schema.edition e\n" +
//        "    JOIN library_schema.distribution d ON d.edition_id = e.edition_id\n" +
//        "    JOIN library_schema.reader r ON r.reader_id = d.reader_id\n" +
//        "    JOIN library_schema.bookposition bp ON bp.position_id = e.position_id\n" +
//        "    JOIN library_schema.storage s ON s.storage_id = bp.storage_id\n" +
//        "WHERE \n" +
//        "    r.reader_id = :id \n" +
//        "    AND d.date_give BETWEEN :start AND :finish \n" +
//        "    AND r.library_id != s.library_id", nativeQuery = true)
//    List<Edition> findEditionByReaderInNotAssignedLibrary(@Param("id") Integer readerId,
//                                                          @Param("start") LocalDate start,
//                                                          @Param("finish") LocalDate finish);

    //7 query
    @Query(value = "SELECT \n" +
            "    e.*, 0 AS clazz_\n" +
            "FROM \n" +
            "    library_schema.edition e \n" +
            "    JOIN library_schema.distribution d ON e.edition_id = d.edition_id \n" +
            "    JOIN library_schema.bookposition p ON e.position_id = p.position_id \n" +
            "    JOIN library_schema.storage s ON p.storage_id = s.storage_id \n" +
            "    JOIN library_schema.library l ON l.library_id=s.library_id\n" +
            "WHERE \n" +
            "    d.date_return IS NULL \n" +
            "    AND l.address = :address \n" +
            "    AND s.room_number = :room \n" +
            "    AND p.rack_number = :rack \n" +
            "    AND p.shelf_number = :shelf", nativeQuery = true)
    List<Edition> findGivenEditionByPosition(@Param("address") String address, @Param("room") Integer room,
                                             @Param("rack") Integer rack, @Param("shelf") Integer shelf);

    //11 query
    @Query(value = "SELECT \n" +
            "    e.edition_code, p.title, a.firstname, a.lastname\n" +
            "FROM \n" +
            "    library_schema.edition e \n" +
            "    JOIN library_schema.publication p ON p.publication_id = e.publication_id\n" +
            "    JOIN library_schema.author a ON a.author_id = p.author_id\n" +
            "WHERE \n" +
            "    e.date_arrived BETWEEN :start AND :finish ", nativeQuery = true)
    List <Object[]> findEditionByArrivedDate(@Param("start") LocalDate start, @Param("finish") LocalDate finish);
//
//    List<Edition> findEditionByLeftDate();
//
    //14 query
    @Query(value = "SELECT \n" +
            "    e.* , 0 AS clazz_  \n" +
            "FROM \n" +
            "    library_schema.edition e \n" +
            "    JOIN library_schema.publication p ON p.publication_id = e.publication_id\n" +
            "WHERE \n" +
            "    p.publication_id = :id\n", nativeQuery = true)
    List<Edition> findEditionByPublication(@Param("id") Integer id);

    //15 query
    @Query(value = "SELECT \n" +
            "    e.*, 0 AS clazz_ \n" +
            "FROM \n" +
            "    library_schema.edition e \n" +
            "    JOIN library_schema.publication p ON p.publication_id = e.publication_id\n" +
            "WHERE \n" +
            "    p.author_id = :id", nativeQuery = true)
    List<Edition> findEditionByAuthor(@Param("id") Integer id);


}
