import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

class DAO<T> implements DaoTemplate{

    private static DAO dao;
    private DAO() {};

    public static DAO getInstance(){
        if(dao == null){
            dao = new DAO();
        }
        return dao;
    }

    static void setSchema() {
        String query = "SET search_path TO orm";
        try {
            PreparedStatement ps = ConnectionUtil.connect().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.executeQuery();
        } catch (SQLException e) {
            return;
        }
    }

    @Override
    public boolean select(Object o) {
        return false;
    }

    @Override
    public boolean selectAll(Object o) {
        return false;
    }

    static String getType(String types) {
        switch (types) {
            case "String":
                return "text";
            case "Double":
                return "decimal(32,2)";
            default:
                return types;
        }
    }
    @Override
    public void create(MyObject o, Mapping m) {
        try {
            StringBuilder str = new StringBuilder();
            String type = getType(o.getPkey().getType().getSimpleName());
            str.append("create table if not exists \"" + o.getC().getSimpleName().toString() + "\"(");
            if (o.getPkey() != null) {
                str.append("\r\n\"" + o.getPkey().getName() + "\" " + type + " primary key,\r\n");
            } else {
                str.append("\r\n\"" + o.getClass().getSimpleName() + "_id\" serial primary key,\r\n");
            }
            // actual field objects
            Field[] fs = o.getFields();
            // field names for column titles in sql
            String[] fields = m.getFieldNames(fs);
            StringBuilder otherColumns = new StringBuilder();
            otherColumns.append((String) Arrays.stream(fields).filter(x -> !x.equals(o.getPkey().
                    getName())).map(y -> y.replace(y, y + " ").toString()).
                    collect(StringBuilder::new, StringBuilder::append, StringBuilder::append).toString());
            // field names excluding the primary key
            String[] cols = otherColumns.toString().split(" ");
            // field data types
            String[] types = Arrays.stream(fs).map(x -> x.getType().getSimpleName()).toArray(String[]::new);
            for (int i = 0; i < cols.length; i++) {
                str.append("\"" + cols[i] + "\" ");
                str.append(getType(types[i]));
                if (i < cols.length-1) {
                    str.append(",\r\n");
                }
            }
            str.append(")");
        //  System.out.println(str);
            PreparedStatement ps = ConnectionUtil.connect().prepareStatement(str.toString(), Statement.RETURN_GENERATED_KEYS);
            ps.executeQuery();
        } catch (SQLException e) {
            return;
        }
    }

    @Override
    public int insert(MyObject o, Mapping m) {
        return 0;
    }

    @Override
    public boolean update(Object o) {
        return false;
    }

    @Override
    public boolean delete(Object o) {
        return false;
    }

    @Override
    public boolean commit() {
        return false;
    }

    @Override
    public boolean rollback() {
        return false;
    }

    @Override
    public boolean savepoint() {
        return false;
    }

    @Override
    public boolean begin() {
        return false;
    }
}
