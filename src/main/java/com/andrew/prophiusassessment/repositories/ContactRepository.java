package com.andrew.prophiusassessment.repositories;

import com.andrew.prophiusassessment.entity.Contact;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepository extends CrudRepository<Contact, Long> {
}
