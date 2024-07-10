package ru.evorsio.commandwands.math.equation;

import java.util.List;

public class Expression implements Term{

  private final List<Term> terms;
  private final Function function;

  public Expression(List<Term> terms, Function function) {
    this.terms = terms;
    this.function = function;
  }

  @Override
  public double compute(double t) {
    double[] args = terms.stream().map(term -> term.compute(t))
        .mapToDouble(Double::doubleValue)
        .toArray();

    return function.apply(args);
  }
}
