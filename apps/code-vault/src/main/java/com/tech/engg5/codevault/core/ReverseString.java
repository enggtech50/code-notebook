package com.tech.engg5.codevault.core;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

// Reverse a string using java streams.

public class ReverseString {
  public static void main(String[] args) {
    String inputString = "Hello World";
    String output = reverse(inputString);

    System.out.println("Reversed - " + output);
  }

  public static String reverse(String input) {
    return IntStream.range(0, input.length())
      .mapToObj(i -> input.charAt(input.length() - 1 - i))
      .map(String::valueOf)
      .collect(Collectors.joining());
  }
}
