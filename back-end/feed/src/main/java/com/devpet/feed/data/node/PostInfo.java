package com.devpet.feed.data.node;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;

@Node
@Getter
@Setter
@NoArgsConstructor
public class PostInfo {

    @Id
    private String postId;

    @Property
    private String postCategory;
}
