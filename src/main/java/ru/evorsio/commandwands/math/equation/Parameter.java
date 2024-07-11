package ru.evorsio.commandwands.math.equation;

public class Parameter implements Term {

  private final String paramName;
  private final int paramIndex;

  public Parameter(String paramName, int paramIndex) {
    this.paramName = paramName;
    this.paramIndex = paramIndex;
  }

  @Override
  public double compute(double... params) {
    return params[paramIndex];
  }

  public int getParamIndex() {
    return paramIndex;
  }

  public String getParamName() {
    return paramName;
  }
}
