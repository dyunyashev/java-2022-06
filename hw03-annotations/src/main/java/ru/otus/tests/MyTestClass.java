package ru.otus.tests;

import ru.otus.annotations.After;
import ru.otus.annotations.Before;
import ru.otus.annotations.Test;

public class MyTestClass {
    @Before
    public void before1() {
        System.out.println("before 1");
    }

    @Before
    public void before2() {
        System.out.println("before 2");
    }

    @After
    public void after() {
        System.out.println("after");
    }

    @Test
    public void test1() {
        throw new RuntimeException("failed 1");
    }

    @Test
    public void test2() {
        System.out.println("passed 2");
    }

}
