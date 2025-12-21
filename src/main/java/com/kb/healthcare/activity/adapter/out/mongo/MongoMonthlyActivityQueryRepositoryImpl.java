package com.kb.healthcare.activity.adapter.out.mongo;

import com.kb.healthcare.activity.domain.MonthlyActivity;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.BulkOperations;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.repository.support.QuerydslRepositorySupport;

import java.time.LocalDate;
import java.util.List;

import static com.kb.healthcare.activity.adapter.out.mongo.QMonthlyActivityEntity.monthlyActivityEntity;
import static com.kb.healthcare.util.LocalDateUtil.formatDefaultPattern;
import static java.time.LocalDate.now;
import static java.util.UUID.fromString;
import static org.springframework.data.mongodb.core.BulkOperations.BulkMode.UNORDERED;
import static org.springframework.util.CollectionUtils.isEmpty;

class MongoMonthlyActivityQueryRepositoryImpl extends QuerydslRepositorySupport implements MongoMonthlyActivityQueryRepository {

    private final MongoOperations mongoOperations;

    public MongoMonthlyActivityQueryRepositoryImpl(MongoOperations operations) {
        super(operations);

        this.mongoOperations = operations;
    }

    @Override
    public void upsert(List<MonthlyActivity> monthlyActivities) {
        if (isEmpty(monthlyActivities)) {
            return;
        }

        BulkOperations bulkOps = mongoOperations.bulkOps(
                UNORDERED,
                MonthlyActivityEntity.class
        );

        for (MonthlyActivity monthlyActivity : monthlyActivities) {
            Query query = new Query(
                    Criteria.where(monthlyActivityEntity.userId.getMetadata().getName()).is(fromString(monthlyActivity.userId()))
                            .and(monthlyActivityEntity.date.getMetadata().getName()).is(formatDefaultPattern(monthlyActivity.date()))
                            .and(monthlyActivityEntity.type.getMetadata().getName()).is(monthlyActivity.type())
            );

            Update update = new Update()
                    .inc(monthlyActivityEntity.steps.getMetadata().getName(), monthlyActivity.steps())
                    .inc(monthlyActivityEntity.calories.getMetadata().getName(), monthlyActivity.calories().value())
                    .inc(monthlyActivityEntity.distance.getMetadata().getName(), monthlyActivity.distance().value())
                    .setOnInsert(monthlyActivityEntity.createdAt.getMetadata().getName(), now());

            bulkOps.upsert(query, update);
        }

        bulkOps.execute();
    }

    @Override
    public Page<MonthlyActivityEntity> find(FindAggregatedActivityQuery query) {
        Pageable pageable = PageRequest.of(
                query.page(),
                query.limit()
        );

        return from(monthlyActivityEntity)
                .where(
                        monthlyActivityEntity.userId.eq(fromString(query.userId())),
                        resolveDateBetween(
                                query.startDate(),
                                query.endDate()
                        )
                )
                .orderBy(monthlyActivityEntity.date.desc())
                .fetchPage(pageable);
    }

    private BooleanExpression resolveDateBetween(
            LocalDate startDate,
            LocalDate endDate
    ) {
        if (startDate == null && endDate == null) {
            return null;
        }

        if (startDate == null) {
            return monthlyActivityEntity.date.loe(formatDefaultPattern(endDate));
        }

        if (endDate == null) {
            return monthlyActivityEntity.date.goe(formatDefaultPattern(startDate));
        }

        return monthlyActivityEntity.date.between(
                formatDefaultPattern(startDate),
                formatDefaultPattern(endDate)
        );
    }

}
