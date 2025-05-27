package com.tech.engg5.codevault.core;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

// Count occurrence of each character in a string.

public class CharacterOccurrence {

  public static void main(String[] args) {
    String inputString = "development";

    Map<Character, Long> charCountMp = inputString.chars()
      .mapToObj(c -> (char) c)
      .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

    charCountMp.forEach((character, count) -> System.out.println(character + ": " + count));
  }
}
