package ru.otus;

import ru.otus.tests.MyTestClass;
import ru.otus.tests.MyTestClass2;

public class MainApp {
    public static void main(String[] args) {
        LaunchTests.launch(MyTestClass.class);
        LaunchTests.launch(MyTestClass2.class);
    }
}
