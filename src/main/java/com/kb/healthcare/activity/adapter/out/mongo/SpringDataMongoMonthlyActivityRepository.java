package com.kb.healthcare.activity.adapter.out.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;

interface SpringDataMongoMonthlyActivityRepository extends MongoRepository<MonthlyActivityEntity, String>, MongoMonthlyActivityQueryRepository {
}
