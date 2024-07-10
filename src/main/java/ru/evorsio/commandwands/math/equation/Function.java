package ru.evorsio.commandwands.math.equation;

public interface Function {

  double apply(double... args);

  int getArity();

}
