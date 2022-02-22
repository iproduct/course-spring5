package course.ws.dao;

public interface IdGenerator<K>{
    K getNextId();
}
