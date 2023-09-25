package sample.controller;

public class Employee {
    String name;
    int id;

    @Override
    public String toString() {
        String value = id+"("+name+")";
        return value;
    }
}
