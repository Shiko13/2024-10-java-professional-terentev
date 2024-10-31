package ru.otus;

import com.google.common.base.Strings;

public class HelloOtus {
    public static void main(String[] args) {
        String message = "Hello, Otus!";
        System.out.println("Message: " + message);

        String repeatedMessage = Strings.repeat(message + " ", 3);
        System.out.println("Repeated message: " + repeatedMessage);
    }
}