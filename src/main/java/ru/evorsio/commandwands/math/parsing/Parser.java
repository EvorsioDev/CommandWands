package ru.evorsio.commandwands.math.parsing;

import ru.evorsio.commandwands.math.equation.Term;

public interface Parser {

  Term parse(String input) throws ParserException;
}
