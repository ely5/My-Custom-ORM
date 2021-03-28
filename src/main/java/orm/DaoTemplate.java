package orm;

public interface DaoTemplate<T,K> {

    public boolean select(K k);
    public boolean selectAll(K k);
    public void create(MyObject o, Mapping m);
    public void insert(MyObject o, Mapping m);
    public boolean update(K k) ;
    public boolean delete(K k) ;
    public boolean commit() ;
    public boolean rollback() ;
    public boolean savepoint() ;
    public boolean begin() ;
}
