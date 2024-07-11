package ru.evorsio.commandwands.math.parsing.impl;

import java.util.Map;
import lombok.Getter;
import ru.evorsio.commandwands.math.parsing.ParserException;
import ru.evorsio.commandwands.math.parsing.Token;
import ru.evorsio.commandwands.math.parsing.TokenType;

@Getter
public final class Tape {

  private static final Map<Character, TokenType> symbolTokens = Map.of(
      '+', TokenType.PLUS,
      '-', TokenType.MINUS,
      '*', TokenType.ASTERISK,
      '/', TokenType.SLASH,
      ',', TokenType.COMMA,
      ')', TokenType.PAREN_RIGHT,
      '(', TokenType.PAREN_LEFT
  );
  private final String input;
  private int start;
  private int end;
  private Token previous;

  public Tape(String input) {
    this.input = input;
  }

  public boolean isSymbol() {
    return symbolTokens.containsKey(input.charAt(start));
  }

  public String getRegion() {
    return input.substring(start, end + 1);
  }

  public Token readNumber() throws ParserException {
    boolean fflag = false;
    while (end < input.length()) {
      if (input.charAt(end) == '.') {
        if (fflag) {
          throw new ParserException("Illegal radix", start, end, input);
        }
        fflag = true;
      } else if (!Character.isDigit(input.charAt(end))) {
        break;
      }
      end++;
    }

    if (input.charAt(end - 1) == '.') {
      throw new ParserException("Number is not followed after radix", end - 1, end - 1, input);
    }
    Token t = new Token(input, start, end - 1, TokenType.NUMBER, previous);
    start = end;
    return (previous = t);
  }

  public Token readString() {
    while (end < input.length() && (Character.isLetter(input.charAt(end)) || Character.isDigit(input.charAt(end)))) {
      end++;
    }
    Token t = new Token(input, start, end - 1, TokenType.QUALIFIER, previous);
    start = end;
    return (previous = t);
  }

  public Token readSymbol() {
    String current = input.substring(start, end + 1);
    TokenType tt = symbolTokens.get(current.charAt(0));
    Token token = new Token(input, start, end, tt, previous);
    end++;
    start++;
    return (previous = token);
  }

  public boolean isDone() {
    return end >= input.length();
  }

  public void skip() {
    int l = getRegion().length();
    end += l;
    start += l;
  }

  @Override
  protected Object clone() throws CloneNotSupportedException {
    Tape t = new Tape(input);
    t.start =start;
    t.end = end;
    t.previous = previous;
    return t;
  }
}
