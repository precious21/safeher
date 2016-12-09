package com.tgi.safeher.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.tgi.safeher.entity.Person;

public interface PersonRepository extends MongoRepository<Person, String>{
}
