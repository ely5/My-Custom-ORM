package usermodel;

import orm.Mapping;
public class Driver{

    // object/relational mapping.
    // user inserts json file or java objects to orm.Mapping class

    public static void main(String[] args) throws Exception {

        long startTime = System.nanoTime();

        Mapping m = new Mapping(Person.class, Animal.class);

        Person steven = new Person("Steven", "Crain", "male", 37, "horse");
        Person nell = new Person("Eleanor", "Crain", "female", 30, "bird");
        m.persist(steven);
        m.persist(nell);

        m.persist("boo.json", Person.class);
        m.persist("person1.json", Person.class);

        m.persist(new Animal("dog", 5, 3.34, 2));
        m.persist("animal.json", Animal.class);

        m.selectAll("Person");
        m.selectAll("Animal");

        m.delete("Janet", Person.class);
        m.delete("dog", Animal.class);

        Person nell_new = new Person("Eleanor", "Crain", "female", 31, "bird");

        m.update(nell_new);

        m.selectAll("Person");
        m.selectAll("Animal");

        long endTime = System.nanoTime();
        System.out.println("Execution time in milliseconds : " +
                (endTime-startTime) / 1000000);
    }
}
