package ru.evorsio.commandwands.math.parsing.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import ru.evorsio.commandwands.math.equation.Term;
import ru.evorsio.commandwands.math.parsing.Parser;
import ru.evorsio.commandwands.math.parsing.ParserException;
import ru.evorsio.commandwands.math.parsing.TokenType;

class ParserImplTest {

  @Test
  void testOperations() {
    Parser parser = new ParserImpl();
    Term output = assertDoesNotThrow(() -> parser.parse("1 + 2.0"));
    assertEquals(3.0, output.compute(0));

    output = assertDoesNotThrow(() -> parser.parse("1 * 2"));
    assertEquals(2, output.compute(0));

    output = assertDoesNotThrow(() -> parser.parse("2 / 4"));
    assertEquals(0.5, output.compute(0));

    output = assertDoesNotThrow(() -> parser.parse("1 - 2"));
    assertEquals(-1, output.compute(0));

    output = assertDoesNotThrow(() -> parser.parse("-1"));
    assertEquals(-1, output.compute(0));
  }

  @Test
  void testOperations_with_parameter() {
    Parser parser = new ParserImpl();
    Term output = assertDoesNotThrow(() -> parser.parse("1 + t"));
    assertEquals(3.0, output.compute(2));

    output = assertDoesNotThrow(() -> parser.parse("t * 2"));
    assertEquals(2, output.compute(1));

    output = assertDoesNotThrow(() -> parser.parse("2 / t"));
    assertEquals(0.5, output.compute(4));

    output = assertDoesNotThrow(() -> parser.parse("1 - t"));
    assertEquals(-1, output.compute(2));

    output = assertDoesNotThrow(() -> parser.parse("-t"));
    assertEquals(-1, output.compute(1));
  }


  @Test
  void testOperations_with_groups() {
    Parser parser = new ParserImpl();
    Term output = assertDoesNotThrow(() -> parser.parse("2+(1 + t)"));
    assertEquals(5, output.compute(2));

    output = assertDoesNotThrow(() -> parser.parse("2+(1 - t)"));
    assertEquals(4, output.compute(-1));

    output = assertDoesNotThrow(() -> parser.parse("2+(10 * t)"));
    assertEquals(-8, output.compute(-1));

    output = assertDoesNotThrow(() -> parser.parse("2+(10 / t)"));
    assertEquals(7, output.compute(2));


    output = assertDoesNotThrow(() -> parser.parse("2-(1 + t)"));
    assertEquals(-1, output.compute(2));

    output = assertDoesNotThrow(() -> parser.parse("2-(1 - t)"));
    assertEquals(0, output.compute(-1));

    output = assertDoesNotThrow(() -> parser.parse("2-(10 * t)"));
    assertEquals(12, output.compute(-1));

    output = assertDoesNotThrow(() -> parser.parse("2-(10 / t)"));
    assertEquals(-3, output.compute(2));


    output = assertDoesNotThrow(() -> parser.parse("2*(1 + t)"));
    assertEquals(6, output.compute(2));

    output = assertDoesNotThrow(() -> parser.parse("2*(1 - t)"));
    assertEquals(4, output.compute(-1));

    output = assertDoesNotThrow(() -> parser.parse("2*(10 * t)"));
    assertEquals(-20, output.compute(-1));

    output = assertDoesNotThrow(() -> parser.parse("2*(10 / t)"));
    assertEquals(10, output.compute(2));


    output = assertDoesNotThrow(() -> parser.parse("2/(1 + t)"));
    assertEquals(1, output.compute(1));

    output = assertDoesNotThrow(() -> parser.parse("2/(1 - t)"));
    assertEquals(1, output.compute(-1));

    output = assertDoesNotThrow(() -> parser.parse("2/(10 * t)"));
    assertEquals(-0.2, output.compute(-1));

    output = assertDoesNotThrow(() -> parser.parse("2/(10 / t)"));
    assertEquals(0.4, output.compute(2));

    output = assertDoesNotThrow(() -> parser.parse("-(10 + t)"));
    assertEquals(-10, output.compute(0));
  }

  @Test
  void testErrors() throws ParserException {
    Parser parser = new ParserImpl();
    assertThrows(ParserException.class, () -> parser.parse("2+(1 + t"));
    assertThrows(ParserException.class, () -> parser.parse("2+1 + t)"));

    assertThrows(ParserException.class, () -> parser.parse("2+((1 + t)"));
    assertThrows(ParserException.class, () -> parser.parse("2+((1 + t))))"));
    assertThrows(ParserException.class, () -> parser.parse("+1"));
    assertThrows(ParserException.class, () -> parser.parse("1+"));
    assertThrows(ParserException.class, () -> parser.parse("t()"));
    assertThrows(ParserException.class, () -> parser.parse(","));
    assertThrows(ParserException.class, () -> parser.parse(",("));
    assertThrows(ParserException.class, () -> parser.parse("pow(,)"));
    assertThrows(ParserException.class, () -> parser.parse("pow(1,)"));
    assertThrows(ParserException.class, () -> parser.parse("pow(,1)"));
    assertThrows(ParserException.class, () -> parser.parse("pow(+1,1)"));
    assertThrows(ParserException.class, () -> parser.parse("pow(1)"));
    assertThrows(ParserException.class, () -> parser.parse("pow(1, 1, 1)"));
    assertThrows(ParserException.class, () -> parser.parse("1 ^ 1"));
    //assertThrows(ParserException.class, () -> parser.parse("1 2+"));
  }

  @Test
  void testFunctions() throws ParserException {
    Parser parser = new ParserImpl();
    parser.parse("1 * sin(t)");
  }

}