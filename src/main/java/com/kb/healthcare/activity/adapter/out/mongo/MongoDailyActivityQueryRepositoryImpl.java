package com.kb.healthcare.activity.adapter.out.mongo;

import com.kb.healthcare.activity.domain.DailyActivity;
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

import static com.kb.healthcare.activity.adapter.out.mongo.QDailyActivityEntity.dailyActivityEntity;
import static com.kb.healthcare.util.LocalDateUtil.formatDefaultPattern;
import static java.util.UUID.fromString;
import static org.springframework.data.mongodb.core.BulkOperations.BulkMode.UNORDERED;
import static org.springframework.util.CollectionUtils.isEmpty;

class MongoDailyActivityQueryRepositoryImpl extends QuerydslRepositorySupport implements MongoDailyActivityQueryRepository {

    private final MongoOperations mongoOperations;

    public MongoDailyActivityQueryRepositoryImpl(MongoOperations operations) {
        super(operations);

        this.mongoOperations = operations;
    }

    @Override
    public void upsert(List<DailyActivity> dailyActivities) {
        if (isEmpty(dailyActivities)) {
            return;
        }

        BulkOperations bulkOps = mongoOperations.bulkOps(
                UNORDERED,
                DailyActivityEntity.class
        );

        for (DailyActivity dailyActivity : dailyActivities) {
            Query query = new Query(
                    Criteria.where(dailyActivityEntity.userId.getMetadata().getName()).is(fromString(dailyActivity.userId()))
                            .and(dailyActivityEntity.date.getMetadata().getName()).is(formatDefaultPattern(dailyActivity.date()))
                            .and(dailyActivityEntity.type.getMetadata().getName()).is(dailyActivity.type())
            );

            Update update = new Update()
                    .inc(dailyActivityEntity.steps.getMetadata().getName(), dailyActivity.steps())
                    .inc(dailyActivityEntity.calories.getMetadata().getName(), dailyActivity.calories().value())
                    .inc(dailyActivityEntity.distance.getMetadata().getName(), dailyActivity.distance().value())
                    .setOnInsert(dailyActivityEntity.createdAt.getMetadata().getName(), LocalDate.now());

            bulkOps.upsert(query, update);
        }

        bulkOps.execute();
    }

    @Override
    public Page<DailyActivityEntity> find(FindAggregatedActivityQuery query) {
        Pageable pageable = PageRequest.of(
                query.page(),
                query.limit()
        );

        return from(dailyActivityEntity)
                .where(
                        dailyActivityEntity.userId.eq(fromString(query.userId())),
                        resolveDateBetween(
                                query.startDate(),
                                query.endDate()
                        )
                )
                .orderBy(dailyActivityEntity.date.asc())
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
            return dailyActivityEntity.date.loe(formatDefaultPattern(endDate));
        }

        if (endDate == null) {
            return dailyActivityEntity.date.goe(formatDefaultPattern(startDate));
        }

        return dailyActivityEntity.date.between(
                formatDefaultPattern(startDate),
                formatDefaultPattern(endDate)
        );
    }

}
