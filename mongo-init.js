const database = db.getSiblingDB("kb-healthcare");

database.createUser({
    user: "app",
    pwd: "1234",
    roles: [
        { role: "readWrite", db: "kb-healthcare" }
    ]
});