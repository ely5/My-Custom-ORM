import org.junit.Test;
import static org.junit.Assert.*;

import orm.MyObject;
import usermodel.Animal;
import usermodel.Person;

import java.lang.reflect.Field;

public class TestMyObject {

    Animal a = new Animal("cat", 3, 3.0, 3);
    Animal a1 = new Animal("dog", 1, 6.9, 1);
    Person p = new Person("Elizabeth", "Ye","female",22, "rat");
    Person p1 = new Person("David", "Ye", "male", 20, null);
    MyObject m1 = new MyObject(p, null, p.getClass().getDeclaredFields());

    public TestMyObject() throws NoSuchFieldException {
    }

    @Test
    public void testGetObject() {
        assertEquals(p, m1.getO());
    }

    @Test
    public void testSetObject() {
        m1.setO(p1);
        assertEquals(Person.class, m1.getO().getClass());
    }

    @Test
    public void testGetPkey() {
        assertEquals(null, m1.getPkey());
    }

    @Test
    public void testSetPkey() {
        m1.setPkey(m1.getFields()[0]);
        assertEquals(m1.getFields()[0], m1.getPkey());
    }

    @Test
    public void testGetFields() {
        assertEquals(m1.getFields()[0], p.getClass().getDeclaredFields()[0]);
        assertEquals(m1.getFields()[1], p.getClass().getDeclaredFields()[1]);
    }
}
