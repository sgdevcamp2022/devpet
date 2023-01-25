package com.smilegate.devpet.appserver.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document("long_database_sequence")
public class LongDatabaseSequence implements DatabaseSequence<Long> {
    private Long _id;

    public Long getSeq() {
        return _id;
    }
}
