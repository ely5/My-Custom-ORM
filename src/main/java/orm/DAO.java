package orm;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

class DAO<T> implements DaoTemplate{

    private static DAO dao;
    private DAO() {};

    public static DAO getInstance(){
        if(dao == null){
            dao = new DAO();
        }
        return dao;
    }

    private static ArrayList<String> table_list = new ArrayList<String>();
    private static ArrayList<String> query_list = new ArrayList<String>();

    @Override
    public boolean select(Object o) {
        return false;
    }

    @Override
    public boolean selectAll(Object o) {
        return false;
    }


    @Override
    public void create(MyObject o, Mapping m) {
        if (table_list.contains(o.getC().getSimpleName().toString())){
            return;
        }
        try (ConnectionSession sess = new ConnectionSession()){
            StringBuilder str = new StringBuilder();
            String type = getType(o.getPkey().getType().getSimpleName());
            str.append("create table if not exists \"" + o.getC().getSimpleName().toString() + "\"(");

            table_list.add(o.getC().getSimpleName().toString());
            if (o.getPkey() != null) {
                str.append("\r\n\"" + o.getPkey().getName() + "\" " + type + " primary key,\r\n");
            } else {
                str.append("\r\n\"" + o.getClass().getSimpleName() + "_id\" serial primary key,\r\n");
            }
            // actual field objects
            Field[] fs = o.getFields();
            // field names for column titles in sql excluding primary key
            String[] fields = m.getFieldNames(fs);
            StringBuilder otherColumns = new StringBuilder();
            otherColumns.append((String) Arrays.stream(fields).filter(x -> !x.equals(o.getPkey().
                    getName())).map(y -> y.replace(y, y + " ").toString()).
                    collect(StringBuilder::new, StringBuilder::append, StringBuilder::append).toString());
            // field names excluding the primary key
            String[] cols = otherColumns.toString().split(" ");
            // field data types
            String[] types = new String[cols.length];
            int j = 0;     // iterator with primary key
            for (int i = 0; i < types.length; i++) {
                if (!fs[j].equals(o.getPkey())){
                    types[i] = fs[j].getType().getSimpleName();
                    j++;
                }
                else {
                    j++;
                    i--;
                }
            }
            for (int i = 0; i < cols.length; i++) {
                str.append("\"" + cols[i] + "\" ");
                str.append(getType(types[i]));
                if (i < cols.length-1) {
                    str.append(",\r\n");
                }
            }
            str.append(")");
            //System.out.println(str);
            Connection conn = sess.getActiveConnection();
            PreparedStatement ps = conn.prepareStatement(str.toString(), Statement.RETURN_GENERATED_KEYS);
            ps.executeQuery();
        } catch (SQLException e) {
            return;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    Object getGetter(MyObject o, Field f) throws IntrospectionException, InvocationTargetException, IllegalAccessException {
        PropertyDescriptor[] ps = Introspector.getBeanInfo(o.getC()).getPropertyDescriptors();
        for (PropertyDescriptor p : ps) {
            if (p.getReadMethod().toString().toLowerCase().contains(f.getName().toLowerCase(Locale.ROOT))) {
                return p.getReadMethod().invoke(o.getO());
            }
        }
        return null;
    }

    @Override
    public void insert(MyObject o, Mapping m) {
        try (ConnectionSession sess = new ConnectionSession();){
            StringBuilder str = new StringBuilder();
            str.append("insert into \"" + o.getC().getSimpleName().toString() + "\" values (");
            Field[] fs = o.getFields();
            for (Field f : fs) {
                Object methodResult = getGetter(o, f);
                if (methodResult.getClass().getSimpleName().equals("String")) {
                    str.append("'");
                }
                str.append(methodResult);
                if (methodResult.getClass().getSimpleName().equals("String")) {
                    str.append("'");
                }
                str.append(", ");
            }
            str.delete(str.length() - 2, str.length() - 1);
            str.append(")");
            //System.out.println(str.toString());
            Connection conn = sess.getActiveConnection();
            PreparedStatement ps = conn.prepareStatement(str.toString(), Statement.RETURN_GENERATED_KEYS);
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            ps.close();
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
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

    static String getType(String types) {
        switch (types.toLowerCase()) {
            case "int":
                return "integer";
            case "char":
                return "smallint";
            case "string":
                return "text";
            case "double":
                return "decimal(32,2)";
            default:
                return types;
        }
    }
}
