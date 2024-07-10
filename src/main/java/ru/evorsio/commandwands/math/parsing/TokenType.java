package ru.evorsio.commandwands.math.parsing;

public enum TokenType {
  NUMBER,
  PLUS,
  MINUS,
  ASTERISK,
  SLASH,
  PAREN_LEFT,
  PAREN_RIGHT,
  QUALIFIER,
  COMMA;

  public boolean isOperator() {
    return switch (this) {
      case PLUS, MINUS, ASTERISK, SLASH -> true;
      default -> false;
    };
  }

}
