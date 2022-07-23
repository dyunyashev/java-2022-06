package ru.otus.tests;

import ru.otus.annotations.After;
import ru.otus.annotations.Before;
import ru.otus.annotations.Test;

public class MyTestClass2 {
    @Before
    public void before1() {
        System.out.println("before №1");
    }

    @After
    public void after() {
        System.out.println("after №1");
    }

    @Test
    public void test1() {
        throw new RuntimeException("failed 1");
    }
}
