package com.journaldev.bootifulmongodb.dal;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.journaldev.bootifulmongodb.model.Content;
import com.journaldev.bootifulmongodb.model.ContentObject;
import com.journaldev.bootifulmongodb.model.Value;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.limit;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.project;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.skip;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.sort;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.unwind;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Update;

@Repository
public class ContentDALImpl implements ContentDAL {

    @Autowired
    private MongoTemplate mongoTemplate;
    
    //update value
    @Override
    public Content updateMenu(ObjectId idMenu, Content content) {
        Update update = new Update();
        update.set("displayText", content.getDisplayText());
        update.set("displayNumber", content.getDisplayNumber());
        update.set("enabled", content.getEnabled());
        Criteria criteria = Criteria.where("id").is(idMenu);
        mongoTemplate.updateFirst(Query.query(criteria), update, Content.class);
        return content;
    }

    @Override
    public List<Content> getMenu(ObjectId companyId) {
        Query query = new Query();
        Sort orderByNumber = new Sort(Sort.Direction.ASC, "displayNumber");
        query.addCriteria(Criteria.where("companyId").is(companyId));
        query.with(orderByNumber);
        query.fields().exclude("value");
        return mongoTemplate.find(query, Content.class);
    }
    
    @Override
    public List<Content> getHome(ObjectId companyId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("companyId").is(companyId));
        query.fields().slice("value", -5);
        return mongoTemplate.find(query, Content.class);
    }

    //add value
    @Override
    public Value addValue(String menu, ObjectId companyId, Value value) {
        Update update = new Update();
        update.push("value", value);
        Criteria criteria = Criteria.where("companyId").is(companyId).and("menu").is(menu);
        mongoTemplate.updateFirst(Query.query(criteria), update, Content.class);
        return value;
    }
    
    //update value
    @Override
    public Value updateValue(ObjectId idMenu, ObjectId idValue, Value value) {
        Update update = new Update();
        update.set("value.$", value);
        Criteria criteria = Criteria.where("id").is(idMenu).and("value.id").is(idValue);
        mongoTemplate.updateFirst(Query.query(criteria), update, Content.class);
        return value;
    }
    
    @Override
    public Void deleteValue(ObjectId idMenu, ObjectId idValue) {
        Update update = new Update();
        update.unset("value.$");
        Criteria criteria = Criteria.where("id").is(idMenu).and("value.id").is(idValue);
        mongoTemplate.updateFirst(Query.query(criteria), update, Content.class);
        return null;
    }
    
    //get value
    @Override
    public List<Content> getValue(String menu, ObjectId companyId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("menu").is(menu).and("companyId").is(companyId));
        return mongoTemplate.find(query, Content.class);
    }

    @Override
    public List<ContentObject> getOneValue(ObjectId idMenu, ObjectId idValue) {
        Criteria criteria = Criteria.where("id").is(idMenu);
        Criteria criteria2 = Criteria.where("value.id").is(idValue);
        Aggregation agg = newAggregation(
                match(criteria),
                unwind("value"),
                match(criteria2)
        );
        AggregationResults<ContentObject> groupResults = mongoTemplate.aggregate(agg, ContentObject.class, ContentObject.class);
        List<ContentObject> listContent = groupResults.getMappedResults();
        return listContent;
    }
    
    @Override
    public List<ContentObject> getOneValueByName(String menu, ObjectId idValue) {
        Criteria criteria = Criteria.where("menu").is(menu);
        Criteria criteria2 = Criteria.where("value.id").is(idValue);
        Aggregation agg = newAggregation(
                match(criteria),
                unwind("value"),
                match(criteria2)
        );
        AggregationResults<ContentObject> groupResults = mongoTemplate.aggregate(agg, ContentObject.class, ContentObject.class);
        List<ContentObject> listContent = groupResults.getMappedResults();
        return listContent;
    }

    @Override
    public Page<Content> findValueByCompany(ObjectId companyId, SearchRequestDto searchDto, Pageable pageable) {
//        long total = getCount(companyId);
        long total = 10;
        Criteria criteria = Criteria.where("companyId").is(companyId);
        for (FieldRequestDto field : searchDto.getSearch()) {
            criteria = criteria.and(field.getField()).regex(field.getKey(), "i");
        }
        
        Aggregation agg = newAggregation(
                unwind("value"),
                project("value","menu"),
                match(criteria),
                skip(pageable.getPageNumber() * pageable.getPageSize()),
                limit(pageable.getPageSize()),
                sort(pageable.getSort())
        );
        
        AggregationResults<Content> groupResults = mongoTemplate.aggregate(agg, "content", Content.class);
        List<Content> listContent = groupResults.getMappedResults();
        System.out.println(agg);
        Page<Content> resultPage = new PageImpl<>(listContent, pageable, total);
        return resultPage;
    }

}
