package ru.evorsio.commandwands.math.parsing;

import lombok.Getter;

public class ParserException extends Exception {

  @Getter
  private final int rangeStart;
  @Getter
  private final int rangeEnd;
  private final String input;

  public ParserException(String message, int rangeStart, int rangeEnd, String input) {
    super(message);
    this.rangeStart = rangeStart;
    this.rangeEnd = rangeEnd;
    this.input = input;
  }

  public ParserException(String message, Token token) {
    this(message, token.getStart(), token.getEnd(), token.getInput());
  }



  @Override
  public String getMessage() {
    StringBuilder output = new StringBuilder();
    output.append(super.getMessage());
    output.append('\n');

    int localityStart = Math.max(0, rangeStart - 4);
    int localityEnd = Math.min(input.length(), rangeEnd + 4);

    output.append(input, localityStart, localityEnd);
    output.append('\n');
    output.append(" ".repeat(rangeStart - localityStart));
    output.append("^".repeat(rangeEnd - rangeStart + 1));
    return output.toString();
  }
}
