package ru.javawebinar.topjava.repository.jdbc;

@FunctionalInterface
interface TransactionInterface<T> {
    T execute();
}