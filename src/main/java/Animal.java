@MyAnnotation.SerializableToJson
class Animal {

    @MyAnnotation.JsonElement
    private Integer age;
    @MyAnnotation.JsonElement
    @MyAnnotation.PrimaryKey
    private String name;
    @MyAnnotation.JsonElement
    private Double weight;
    @MyAnnotation.JsonElement
    private Integer siblings;

    public Animal() {
        this.age = 1;
        this.name = "bug";
        this.weight = 0.0;
        this.siblings = 2;
    }

    public Animal(Integer age, String name, Double weight, Integer siblings) {
        this.age = age;
        this.name = name;
        this.weight = weight;
        this.siblings = siblings;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "Animal{" +
                "age=" + age +
                ", name=\"" + name + '\"' +
                ", weight=" + weight +
                ", name=\"" + name + '\"' +
                '}';
    }
}
