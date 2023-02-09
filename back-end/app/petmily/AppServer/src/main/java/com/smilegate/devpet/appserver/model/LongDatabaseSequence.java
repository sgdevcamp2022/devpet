package com.smilegate.devpet.appserver.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "long_database_sequence")
public class LongDatabaseSequence implements DatabaseSequence<Long> {

    @Id
    private String id;

    private long seq;

    @Override
    public Long getSeq() {
        return this.seq;
    }

    public void setSeq(Long seq) {
        this.seq = seq;
    }
}
