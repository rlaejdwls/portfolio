package com.example.coresample.activities.restful.model;

/**
 * Created by tigris on 2017-09-18.
 */
public class TestModel {
    private int type;
    private String name;
    private String age;
    private String nationality;

    public TestModel(int type, String name, String age, String nationality) {
        this.type = type;
        this.name = name;
        this.age = age;
        this.nationality = nationality;
    }

    public int getType() {
        return type;
    }
    public void setType(int type) {
        this.type = type;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getAge() {
        return age;
    }
    public void setAge(String age) {
        this.age = age;
    }
    public String getNationality() {
        return nationality;
    }
    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    @Override
    public String toString() {
        return "TestModel{" +
                "type=" + type +
                ", name='" + name + '\'' +
                ", age='" + age + '\'' +
                ", nationality='" + nationality + '\'' +
                '}';
    }
}
