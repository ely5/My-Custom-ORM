import java.lang.reflect.Field;

public class MyObject {

    public Class c;
    public Field pkey;
    public Field[] fields;

    public MyObject(Class c, Field pkey, Field[] fields) {
        this.c = c;
        this.pkey = pkey;
        this.fields = fields;
    }

    public Class getC() {
        return c;
    }

    public void setC(Class c) {
        this.c = c;
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
