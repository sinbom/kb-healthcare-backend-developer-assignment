const database = db.getSiblingDB("kb-healthcare");

database.createUser({
    user: "app",
    pwd: "1234",
    roles: [
        { role: "readWrite", db: "kb-healthcare" }
    ]
});

database.createCollection('daily_activity');

database.createCollection('monthly_activity');

database.daily_activity.createIndex(
    { "userId": 1, "date": 1, "type": 1 },
    { unique: true, name: "userId_date_type_uindex" }
);

database.monthly_activity.createIndex(
    { "userId": 1, "date": 1, "type": 1 },
    { unique: true, name: "userId_date_type_uindex" }
);