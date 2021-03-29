import org.junit.Test;
import static org.junit.Assert.*;

import orm.Mapping;
import orm.MyObject;
import usermodel.Animal;
import usermodel.Person;

import java.lang.reflect.Field;

public class TestMapping {
    Animal a = new Animal("cat", 3, 3.0, 3);
    Animal a1 = new Animal("dog", 1, 6.9, 1);
    Person p = new Person("Elizabeth", "Ye","female",22, "rat");
    Person p1 = new Person("David", "Ye", "male", 20, null);
    MyObject m1 = new MyObject(p, null, p.getClass().getDeclaredFields());
    Mapping m = new Mapping(Animal.class, Person.class);

    public TestMapping() throws Exception {
    }

    @Test
    public void testGetFields() {
        assertEquals(m.getFields(a)[0].getName(), "name");
        assertEquals(m.getFields(a)[1].getName(), "age");
        assertEquals(m.getFields(a)[2].getName(), "weight");
        assertEquals(m.getFields(a)[3].getName(), "siblings");
    }

    @Test
    public void testGetFieldNames(){
        Field[] f = p.getClass().getDeclaredFields();
        assertEquals(m.getFieldNames(f)[0], "firstName");
        assertEquals(m.getFieldNames(f)[1], "lastName");
        assertEquals(m.getFieldNames(f)[2], "gender");
        assertEquals(m.getFieldNames(f)[3], "age");
        assertEquals(m.getFieldNames(f)[4], "pet");
    }

    @Test
    public void testJsonToObject() throws Exception {
        Person p = new Person("Jane", "Doe", "female", 24, "cat");
        Object o = m.jsonToObject("person1.json", Person.class);
        assertEquals(o.getClass(),Person.class);
        assertEquals(o.toString(), p.toString());
    }
}
