package usermodel;

import orm.MyAnnotation;

@MyAnnotation.SerializableToJson
public class Animal {

    @MyAnnotation.JsonElement
    @MyAnnotation.PrimaryKey
    private String name;
    @MyAnnotation.JsonElement
    private Integer age;
    @MyAnnotation.JsonElement
    private Double weight;
    @MyAnnotation.JsonElement
    private Integer siblings;

    public Animal() {
        this.name = "bug";
        this.age = 1;
        this.weight = 0.0;
        this.siblings = 2;
    }

    public Animal(String name, Integer age, Double weight, Integer siblings) {
        this.name = name;
        this.age = age;
        this.weight = weight;
        this.siblings = siblings;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Integer getSiblings() {
        return siblings;
    }

    public void setSiblings(Integer siblings) {
        this.siblings = siblings;
    }

    @Override
    public String toString() {
        return "usermodel.Animal{" +
                "name=\"" + name + '\"'+
                ", age=\"" + age +
                ", weight=" + weight +
                ", siblings=\"" + siblings + '\"' +
                '}';
    }
}
