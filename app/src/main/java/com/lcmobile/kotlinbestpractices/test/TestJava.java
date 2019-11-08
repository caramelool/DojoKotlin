package com.lcmobile.kotlinbestpractices.test;

public class TestJava {
    public void newPerson() {
        Person person1 = new Person("Name", "LastName");
        Person person2 = new Person("Name");
        Person person3 = Person.create("Name", "LastName");
        Person person4 = Person.create("Name");
    }
}
