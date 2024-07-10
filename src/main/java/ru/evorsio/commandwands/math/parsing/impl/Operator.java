package ru.evorsio.commandwands.math.parsing.impl;

import ru.evorsio.commandwands.math.equation.Function;
import ru.evorsio.commandwands.math.parsing.Token;

record Operator(Token token, Function function) {
}
