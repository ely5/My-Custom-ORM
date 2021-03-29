package orm;

import java.lang.reflect.Field;
import java.util.Arrays;

public class MyObject {

    public Object o;
    public Field pkey;
    public Field[] fields;

    public MyObject(){
        this.o = new String("blah");
        this.pkey = null;
        this.fields = null;
    }

    public MyObject(Object o, Field pkey, Field[] fields) {
        this.o = o;
        this.pkey = pkey;
        this.fields = fields;
    }

    public Class getC() {
        return o.getClass();
    }

    public Object getO() {
        return o;
    }

    public void setO(Object o) {
        this.o = o;
    }

    public Field getPkey() {
        return pkey;
    }

    public void setPkey(Field pkey) {
        this.pkey = pkey;
    }

    public Field[] getFields() {
        return fields;
    }

    public void setFields(Field[] fields) {
        this.fields = fields;
    }

    @Override
    public String toString() {
        return "MyObject { " +
                "Object: " + o.toString() +
                ", pkey=" + pkey.toString() +
                ", fields=" + Arrays.toString(fields) +
                '}';
    }
}
