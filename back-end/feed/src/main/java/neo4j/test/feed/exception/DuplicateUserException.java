package neo4j.test.feed.exception;

public class DuplicateUserException extends RuntimeException {

    public DuplicateUserException() {

    }

    public DuplicateUserException(String message) {
        super(message);
    }
}