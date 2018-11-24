package ru.otus;

import java.util.Objects;

public class UserDataSet extends DataSet {
    @Override
    public String toString() {
        return "{" +
                "age=" + age +
                ", name='" + name + '\'' +
                '}';
    }

    private int age;
    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDataSet that = (UserDataSet) o;
        return age == that.age &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(age, name);
    }

    UserDataSet(int age, String name) {
        this.age = age;
        this.name = name;
    }

    public UserDataSet() {
    }
}
