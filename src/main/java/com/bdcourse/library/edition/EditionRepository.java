package com.bdcourse.library.edition;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EditionRepository extends CrudRepository<Edition, Integer> {

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
//    //14 query
//    List<Edition> findEditionByPublicationNameAndAuthor();
//
//    //15 query
//    List<Edition> findEditionByAuthor();


}
