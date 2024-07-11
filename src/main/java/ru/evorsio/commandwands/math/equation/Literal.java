package ru.evorsio.commandwands.math.equation;

import lombok.ToString;

@ToString
public class Literal implements Term {

  private final double value;

  public Literal(double value) {
    this.value = value;
  }

  @Override
  public double compute(double... params) {
    return value;
  }

  public double getValue() {
    return value;
  }
}
