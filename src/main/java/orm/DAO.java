package orm;

import java.sql.*;
import java.util.*;


class DAO<T> implements DaoTemplate{

    private static DAO dao;
    private DAO() {};

    public static DAO getInstance(){
        if(dao == null){
            dao = new DAO();
        }
        return dao;
    }

    static ArrayList<String> created = new ArrayList<>();
    static ArrayList<String> selected = new ArrayList<>();
    static Set<MyObject> objects = new HashSet<>();
    static HashMap<String, StringBuilder> database = new HashMap<>();

    @Override
    public boolean select(Object o) {
        return false;
    }

    static Object getSQLType(ResultSet rs, int i, String type) throws SQLException {
        switch (type.toLowerCase()) {
            case "integer":
            case "int4":
                return rs.getInt(i);
            case "tinyint":
                return rs.getByte(i);
            case "smallint":
                return rs.getShort(i);
            case "bigint":
                return rs.getLong(i);
            case "real":
                return rs.getFloat(i);
            case "bit":
                return rs.getBoolean(i);
            case "varchar":
            case "char":
            case "longvarchar":
            case "text":
                return rs.getString(i);
            case "double":
            case "float":
                return rs.getDouble(i);
            default: return null;
        }
    }

    static int i = 0;
    @Override
    public void selectAll(String tableName, Mapping m) throws Exception {
        if (!selected.contains(tableName)) {
            try (ConnectionSession sess = new ConnectionSession()) {
                Connection conn = sess.getActiveConnection();
                String str = QueryBuilder.selectAllQuery(tableName, m);
                Statement s = conn.createStatement();
                ResultSet rs = s.executeQuery(str);
                ResultSetMetaData rsmd = rs.getMetaData();
                int count = rsmd.getColumnCount();
                String[] columns = new String[count];
                for (int i = 1; i <= count; i++) {
                    columns[i - 1] = rsmd.getColumnLabel(i);
                }
                StringBuilder queryResult = new StringBuilder();
                queryResult.append("TABLE " + tableName.toUpperCase() + "\r\n");
                while (rs.next()) {
                    for (int i = 1; i <= count; i++) {
                        if (i > 1) {
                            queryResult.append(",  ");
                        }
                        queryResult.append(columns[i - 1] + ": " + rs.getString(i));
                    }
                    queryResult.append("\r\n");
                }
                System.out.println(queryResult.toString());
                database.put(tableName,queryResult);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        else {
            System.out.println(database.get(tableName));
        }
    }


    @Override
    public void create(MyObject o, Mapping m) {
        if (created.contains(o.getC().getSimpleName())){
            return;
        }
        try (ConnectionSession sess = new ConnectionSession()){
            String str = QueryBuilder.createQuery(o,m);
            Connection conn = sess.getActiveConnection();
            PreparedStatement ps = conn.prepareStatement(str, Statement.RETURN_GENERATED_KEYS);
            ps.executeQuery();
            created.add(o.getC().getSimpleName());
        } catch (SQLException e) {
            return;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void insert(MyObject o, Mapping m) {
        try (ConnectionSession sess = new ConnectionSession()){
            Connection conn = sess.getActiveConnection();
            String str = QueryBuilder.insertQuery(o,m);
            PreparedStatement ps = conn.prepareStatement(str, Statement.RETURN_GENERATED_KEYS);
            ps.executeUpdate();
            ps.close();
            objects.add(o);
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(MyObject o, Mapping m) throws Exception {
        o.getFields()[0].setAccessible(true);
        Object ob = o.getFields()[0].get(o.getO());
        m.delete(ob.toString(), o.getC());
        insert(o,m);
    }

    @Override
    public void delete(String id, Class c, Mapping m) throws Exception {
        try (ConnectionSession sess = new ConnectionSession()){
            StringBuilder str = new StringBuilder();
            str.append("delete from " + c.getSimpleName() + " where ");
            Connection conn = sess.getActiveConnection();

            Statement s = conn.createStatement();
            ResultSet r = s.executeQuery("select * from " + c.getSimpleName() + " where false");
            ResultSetMetaData rsmd = r.getMetaData();
            String pk = rsmd.getColumnLabel(1);

            str.append("\"" + pk + "\" = '" + id + "'");
            PreparedStatement ps = conn.prepareStatement(str.toString());
            ResultSet rs = ps.executeQuery();
        }
        catch (SQLException e) {
            return;
        }
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
}
