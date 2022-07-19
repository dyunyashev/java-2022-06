package ru.otus;


import java.util.*;

public class CustomerService {

    //todo: 3. надо реализовать методы этого класса
    //важно подобрать подходящую Map-у, посмотрите на редко используемые методы, они тут полезны
    private final NavigableMap<Customer, String> map = new TreeMap<>((o1, o2) -> (int) (o1.getScores() - o2.getScores()));

    public Map.Entry<Customer, String> getSmallest() {
        //Возможно, чтобы реализовать этот метод, потребуется посмотреть как Map.Entry сделан в jdk
        var smallest = map.firstEntry();
        return (smallest == null) ? null :
                Map.entry(new Customer(smallest.getKey().getId(),
                                        smallest.getKey().getName(),
                                        smallest.getKey().getScores()),
                            smallest.getValue());
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {
        var next = map.higherEntry(customer);
        return (next == null) ? null :
                Map.entry(new Customer(next.getKey().getId(),
                                        next.getKey().getName(),
                                        next.getKey().getScores()),
                            next.getValue());
    }

    public void add(Customer customer, String data) {
        map.put(customer, data);
    }
}
