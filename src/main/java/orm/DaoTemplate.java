package orm;

import java.lang.reflect.Field;

public interface DaoTemplate<T,K> {

    public boolean select(K k);
    public void selectAll(String tableName, Mapping m) throws Exception;
    public void create(MyObject o, Mapping m);
    public void insert(MyObject o, Mapping m);
    public void update(MyObject o) ;
    public boolean delete(K k) ;
    public boolean commit() ;
    public boolean rollback() ;
    public boolean savepoint() ;
}
