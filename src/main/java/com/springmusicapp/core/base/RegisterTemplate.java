package com.springmusicapp.core.base;

public interface RegisterTemplate<T> {
    String id();
    String email();
    String name();
    T userInput();
}
