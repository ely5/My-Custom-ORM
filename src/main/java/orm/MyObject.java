package orm;

import java.lang.reflect.Field;

public class MyObject {

    public Object o;
    public Field pkey;
    public Field[] fields;

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
}
