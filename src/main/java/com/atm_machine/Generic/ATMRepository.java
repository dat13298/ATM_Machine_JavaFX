package com.atm_machine.Generic;

public interface ATMRepository<T> {
    T save(T t);
    T update(T t);
    T findById(String id);
}
