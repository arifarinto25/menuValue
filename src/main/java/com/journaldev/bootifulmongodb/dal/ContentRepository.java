package com.journaldev.bootifulmongodb.dal;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.journaldev.bootifulmongodb.model.Content;
import org.bson.types.ObjectId;

@Repository
public interface ContentRepository extends MongoRepository<Content, String> {

    public Content findByCompanyIdAndMenu(ObjectId id, String menu);
}
