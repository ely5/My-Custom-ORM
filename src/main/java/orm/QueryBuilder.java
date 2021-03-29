package orm;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

public class QueryBuilder {

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

    static Object getGetter(MyObject o, Field f) throws IntrospectionException, InvocationTargetException, IllegalAccessException {
        PropertyDescriptor[] ps = Introspector.getBeanInfo(o.getC()).getPropertyDescriptors();
        for (PropertyDescriptor p : ps) {
            if (p.getReadMethod().toString().toLowerCase().contains(f.getName().toLowerCase(Locale.ROOT))) {
                return p.getReadMethod().invoke(o.getO());
            }
        }
        return null;
    }

    public static ArrayList<Method> getSetters(Class<?> c) throws IntrospectionException {
        ArrayList<Method> list = new ArrayList<Method>();
        Method[] methods = c.getDeclaredMethods();
        for (Method method : methods)
            if (method.toString().contains("set"))
                list.add(method);
        return list;
    }

    public static String selectAllQuery(String tableName, Mapping m) {
        ArrayList<MyObject> temp = new ArrayList<MyObject>();
        return new String("select * from " + tableName);
    }
    public static String createQuery(MyObject o, Mapping m){
        StringBuilder str = new StringBuilder();
        str.append("create table if not exists " + o.getC().getSimpleName().toString() + "(");
        DAO.created.add(o.getC().getSimpleName().toString());
        String pk;
        if (o.getPkey() != null) {
            String type = getType(o.getPkey().getType().getSimpleName());
            str.append("\r\n\"" + o.getPkey().getName() + "\" " + type + " primary key,\r\n");
            pk = o.getPkey().getName();
        } else {
            str.append("\r\n\"" + o.getC().getSimpleName() + "_id\" serial primary key,\r\n");
            pk = o.getC().getSimpleName() + "_id";
        }
        // actual field objects
        Field[] fs = o.getFields();
        // field names for column titles in sql excluding primary key
        String[] fields = m.getFieldNames(fs);
        StringBuilder otherColumns = new StringBuilder();
        otherColumns.append((String) Arrays.stream(fields).filter(x -> !x.equals(pk)).map(y -> y.replace(y, y + " ").toString()).
                collect(StringBuilder::new, StringBuilder::append, StringBuilder::append).toString());
        // field names excluding the primary key
        String[] cols = otherColumns.toString().split(" ");
        // field data types
        String[] types = new String[cols.length];
        int j = 0;     // iterator with primary key
        // TODO refactor this
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
        return str.toString();
    }

    public static String insertQuery(MyObject o, Mapping m) throws IllegalAccessException, IntrospectionException, InvocationTargetException {
        StringBuilder str = new StringBuilder();
        str.append("insert into " + o.getC().getSimpleName() + " values (");
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
        return str.toString();
    }

    public static String updateQuery(MyObject o, Mapping m) {
        StringBuilder str = new StringBuilder();
        str.append("insert into " + o.getC().getSimpleName() + " values (");
        return str.toString();
    }

}
