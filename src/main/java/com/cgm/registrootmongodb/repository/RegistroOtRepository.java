package com.cgm.registrootmongodb.repository;

import com.cgm.registrootmongodb.entity.ResgistroOt;
import com.cgm.registrootmongodb.enumeration.Estado;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface RegistroOtRepository extends MongoRepository<ResgistroOt, String > {
    List<ResgistroOt> findByEstado(Estado estado);

    @Query("{estado : {$ne : ?0} }")
    List<ResgistroOt> findNotEstado(Estado estado);


}
