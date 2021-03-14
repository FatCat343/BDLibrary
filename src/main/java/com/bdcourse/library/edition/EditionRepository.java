package com.bdcourse.library.edition;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EditionRepository extends CrudRepository<Edition, Integer> {

    @Override
    @Query(value = "SELECT *, 0 AS clazz_ FROM library_schema.edition e WHERE e.edition_id != 0", nativeQuery = true)
    Iterable<Edition> findAll();

//    @Query(value = "SELECT d FROM Distribution d JOIN FETCH d.edition e WHERE d.id = :id")
//    Edition findEditionByIdFetchPublication(@Param("id") Integer id);
    //    //5 query
//    List<Edition> findEditionByReaderInAssignedLibrary();
//
//    //6 query
//    List<Edition> findEditionByReaderInNotAssignedLibrary();
//
//    //7 query
//    List<Edition> findGivenEditionByPosition();
//
//    //11 query
//    List <Edition> findEditionByArrivedDate();
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
//
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
