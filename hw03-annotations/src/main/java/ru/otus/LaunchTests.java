package ru.otus;

import ru.otus.annotations.After;
import ru.otus.annotations.Before;
import ru.otus.annotations.Test;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class LaunchTests {
    private static final List<Method> beforeMethods = new ArrayList<>();
    private static final List<Method> afterMethods = new ArrayList<>();
    private static final List<Method> testMethods = new ArrayList<>();

    private static Class<?> testClazz;

    private static int passed;

    public static void launch(Class<?> testClass) {
        testClazz = testClass;

        // открываем тесты
        initTests();

        // запускаем тесты
        runTests();

        // печатаем статистику
        printStatistics(testMethods.size());

        // закрываем тесты
        closeTests();
    }

    private static void runTests() {
        for (var testMethod : testMethods) {
            // для каждого теста создаем новый объект
            Object object = createNewInstance();

            // сначала выполняем befor-методы
            runBeforMethods(object);

            // затем выполняем сам метод теста
            runTestMethod(testMethod, object);

            // в конце выполняем after-методы
            runAfterMethods(object);
        }
    }

    private static void closeTests() {
        beforeMethods.clear();
        afterMethods.clear();
        testMethods.clear();
        passed = 0;

        System.out.println("======== Окончание тестирования класса " + testClazz);
    }

    private static void notFoundConstructorWithoutParameters() {
        try {
            testClazz.getDeclaredConstructor();
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("В классе " + testClazz + " не найден конструктор без параметров!");
        }
    }

    private static void notFoundMethodsForTesting() {
        if (testMethods.size() == 0) {
            throw new RuntimeException("В классе \" + testClass + \" не найдено методов для тестирования!");
        }
    }

    private static void initTests() {
        System.out.println("======== Начало тестирования класса " + testClazz);

        // проверка на наличие конструктора без параметров
        notFoundConstructorWithoutParameters();

        for (var method : testClazz.getDeclaredMethods()) {
            initMethod(method);
        }

        // проверка на наличие тестов в классе
        notFoundMethodsForTesting();
    }

    private static void initMethod(Method method) {
        method.setAccessible(true);

        if (method.isAnnotationPresent(Before.class)) {
            beforeMethods.add(method);
        }
        else if (method.isAnnotationPresent(After.class)) {
            afterMethods.add(method);
        }
        else if (method.isAnnotationPresent(Test.class)) {
            testMethods.add(method);
        }
    }

    private static Object createNewInstance() {
        try {
            return testClazz.getDeclaredConstructor().newInstance();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void printStatistics(int size) {
        System.out.println("Всего тестов: " + size);
        System.out.println("Пройдено тестов: " + passed);
        System.out.println("Упало тестов: " + (size - passed));
    }

    private static void runTestMethod(Method testMethod, Object object) {
        invokeMethodStatistics(testMethod, object);
    }

    private static void runAfterMethods(Object object) {
        for (var afterMethod : afterMethods) {
            invokeMethod(afterMethod, object);
        }
    }

    private static void runBeforMethods(Object object) {
        for (var beforMethod : beforeMethods) {
            invokeMethod(beforMethod, object);
        }
    }

    private static void printError(Method method) {
        System.out.println("Упал метод " + method.getName());
    }

    private static void invokeMethod(Method method, Object object) {
        try {
            method.invoke(object);
        }
        catch (Exception e) {
            printError(method);
        }
    }

    private static void invokeMethodStatistics(Method method, Object object) {
        try {
            method.invoke(object);
            passed++;
        }
        catch (Exception e) {
            printError(method);
        }
    }

}
