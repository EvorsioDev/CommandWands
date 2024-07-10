package ru.evorsio.commandwands.math.parsing;

import lombok.Getter;

@Getter
public class Token {

  public final String input;
  public final int start;
  public int end;
  public final TokenType type;
  private final Token previous;

  public Token(String input, int start, int end, TokenType type, Token previous) {
    this.input = input;
    this.start = start;
    this.end = end;
    this.type = type;
    this.previous = previous;
  }

  @Override
  public String toString() {
    return "Token{" +
        "value='" + getValue() + '\'' +
        ", start=" + start +
        ", end=" + end +
        ", type=" + type +
        '}';
  }

  public String getValue() {
    return input.substring(start, end + 1);
  }
}

