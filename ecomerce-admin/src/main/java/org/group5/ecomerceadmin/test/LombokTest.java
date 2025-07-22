package org.group5.ecomerceadmin.test;

import lombok.Data;

@Data
public class LombokTest {
    private String name;
    private int age;

    public static void main(String[] args) {
        LombokTest test = new LombokTest();
        test.setName("Test");
        test.setAge(25);
        System.out.println("Name: " + test.getName());
        System.out.println("Age: " + test.getAge());
    }
}
