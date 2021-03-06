package orm;

import com.google.gson.Gson;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import usermodel.Animal;
import usermodel.Person;

public class Mapping {

    private Class clazz;
    private String jsonString;

    private Field primaryKey = null;

    public Mapping (Class... classes) throws Exception {
        for (Class c : classes) {
            this.selectAll(c.getSimpleName());
        }
    }

    public Field getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(Field primaryKey) {
        this.primaryKey = primaryKey;
    }

    public Field[] getFields(Object o) {
        Field[] allFields = o.getClass().getDeclaredFields();
        Field[] fields = new Field[allFields.length];
        for (int i = 0; i < allFields.length; i++) {
            Annotation[] annotations = allFields[i].getDeclaredAnnotations();
            Annotation[] pKey = allFields[i].getDeclaredAnnotationsByType(MyAnnotation.PrimaryKey.class);
            if (annotations.length > 0) {
                fields[i] = allFields[i];
            } else fields[i] = null;
            if (pKey.length > 0) {
                primaryKey = fields[i];

            }
        }
        return trimFields(fields);
    }

    public Field[] trimFields(Field[] f) {
        return (Field[]) Arrays.stream(f).filter(x -> x != null).toArray(Field[]::new);
    }

    public String[] getFieldNames(Field[] f) {
        return Arrays.stream(f).map(x -> x.getName()).toArray(String[]::new);
    }

    public Object jsonToObject(String file, Class<? extends Object> clazz) throws Exception {
        jsonToString(file);
        Gson gson = new Gson();
        return gson.fromJson(jsonString, clazz);
    }

    public void preprocess(String s) {
        jsonString = s.trim();
    }

    void jsonToString(String file) throws Exception {
        String str = new String(Files.readAllBytes(Paths.get(file)));
        this.preprocess(str);
    }

    public void persist(Object o) {
        Field[] fields = this.getFields(o);
        MyObject newObj = new MyObject(o, primaryKey, fields);
        DAO.getInstance().create(newObj, this);
        DAO.getInstance().insert(newObj, this);
     }

    public void persist(String file, Class c) throws Exception {
        Object object = this.jsonToObject(file, c);
        Field[] fields = this.getFields(object);
        MyObject newObj = new MyObject(object, primaryKey, fields);
        DAO.getInstance().create(newObj, this);
        DAO.getInstance().insert(newObj, this);
    }

    public void selectAll(String tableName) throws Exception {
        DAO.getInstance().selectAll(tableName, this);
    }

    public void update(Object o) throws Exception {
        Field[] fields = this.getFields(o);
        MyObject newObj = new MyObject(o, primaryKey, fields);
        DAO.getInstance().update(newObj,this);
    }

    public void delete(String id, Class c) throws Exception {
        StringBuilder str = DAO.database.get(c.getSimpleName());
        int remove = c.getDeclaredFields()[0].getName().length();
        int index = str.indexOf(id) - remove - 3;
        int index2 = str.indexOf("\r", index);
        str.delete(index, index2);
        DAO.database.replace(c.getSimpleName(), str);
        DAO.getInstance().delete(id, c, this);
    }
}
