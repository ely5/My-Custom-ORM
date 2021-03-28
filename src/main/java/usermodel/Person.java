package usermodel;

@MyAnnotation.SerializableToJson
public class Person{

    @MyAnnotation.JsonElement
    @MyAnnotation.PrimaryKey
    private String firstName;
    @MyAnnotation.JsonElement
    private String lastName;
    private String gender;
    @MyAnnotation.JsonElement
    private Integer age;
    @MyAnnotation.JsonElement
    private String pet;

    public Person() {
        this.firstName = null;
        this.lastName = null;
        this.gender = null;
        this.age = 0;
        this.pet = null;
    }
    public Person(String firstName, String lastName, String gender, int age, String pet){
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.age = age;
        this.pet = pet;
    }

    public String getFirstName() { return firstName; }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getAge() { return age; }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPet() { return pet; }

    public void setPet(String pet) { this.pet = pet; }

    @Override
    public String toString() {
        return "usermodel.Person{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", gender='" + gender + '\'' +
                ", age=" + age +
                ", pet='" + pet + '\'' +
                '}';
    }
}
