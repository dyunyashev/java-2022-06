package ru.otus;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;

public class HelloOtus {
    public static void main(String[] args) {
        Multiset<String> words = HashMultiset.create();
        words.add("word1");
        words.add("word2");
        words.forEach(System.out::println);
    }
}
