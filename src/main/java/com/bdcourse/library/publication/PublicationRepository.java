package com.bdcourse.library.publication;

import com.bdcourse.library.publication.author.Author;
import jdk.jshell.EvalException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.math.BigInteger;
import java.util.List;

public interface PublicationRepository extends CrudRepository<Publication, Integer> {

    @Override
    @Query(value = "SELECT *, 0 AS clazz_ FROM library_schema.publication WHERE publication_id != 0 " +
            "ORDER BY publication_id ASC", nativeQuery = true)
    Iterable<Publication> findAll();

    //16 query
    @Query(value = "SELECT \n" +
            "    p.title, Max(a.firstname) AS firstname, Max(a.lastname) AS lastname, COUNT(*) AS picked_pieces, 0 AS clazz_ \n" +
            "FROM \n" +
            "    library_schema.publication p \n" +
            "    JOIN library_schema.edition e ON e.publication_id = p.publication_id \n" +
            "    JOIN library_schema.author a ON p.author_id = a.author_id \n" +
            "    JOIN library_schema.distribution d ON d.edition_id = e.edition_id \n" +
            "GROUP BY \n" +
            "    p.publication_id \n" +
            "ORDER BY \n" +
            "    COUNT(*) DESC", nativeQuery = true)
    List<Object[]> findPublicationsByPopularity();

    @Query(value = "SELECT COUNT(p) FROM Publication p WHERE p.author = :author AND p.title = :title AND p.id <> :id")
    BigInteger existsPublicationByAuthorAndTitle(@Param("author") Author author, @Param("title") String title, @Param("id") Integer id);
}
