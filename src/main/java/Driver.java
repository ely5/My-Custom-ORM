import java.lang.reflect.Field;
import java.util.Arrays;

public class Driver{

    // object/relational mapping.
    // user inserts json file or java objects to Mapping class

    public static void main(String[] args) throws Exception {

        long startTime = System.nanoTime();

        Mapping m1 = new Mapping();
        Mapping m2 = new Mapping();

        Animal animal = new Animal(5, "sam", 6.4, 3);
        m1.persist(animal);
        m2.persist("boo.json", Person.class);
        long endTime = System.nanoTime();
        System.out.println("Execution time in milliseconds : " +
                (endTime-startTime) / 1000000);
    }
}
