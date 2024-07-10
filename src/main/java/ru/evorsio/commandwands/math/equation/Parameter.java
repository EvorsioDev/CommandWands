package ru.evorsio.commandwands.math.equation;

public class Parameter implements Term {

  @Override
  public double compute(double t) {
    return t;
  }
}
