package ru.evorsio.commandwands.math.equation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;

@Getter
public enum Operators implements Function {

  ADD(3, 2) {
    @Override
    public double apply(double... args) {
      return args[0] + args[1];
    }
  },
  SUBTRACT(3, 2) {
    @Override
    public double apply(double... args) {
      return args[0] - args[1];
    }
  },
  MULTIPLY(2, 2){
    @Override
    public double apply(double... args) {
      return args[0] * args[1];
    }
  },
  DIVIDE(2, 2){
    @Override
    public double apply(double... args) {
      return args[0] / args[1];
    }
  },
  NEGATE(2, 1){
    @Override
    public double apply(double... args) {
      return -args[0];
    }
  };

  private static final Map<String, List<Operators>> BY_NAME = new HashMap<>();

  private final int precedence;
  private final int arity;

  Operators(int precedence, int arity) {
    this.precedence = precedence;
    this.arity = arity;
  }

  static {
    BY_NAME.put("+", List.of(Operators.ADD));
    BY_NAME.put("-", List.of(SUBTRACT, NEGATE));
    BY_NAME.put("*", List.of(MULTIPLY));
    BY_NAME.put("/", List.of(DIVIDE));
  }

  public static List<Operators> getByName(String name) {
    return BY_NAME.getOrDefault(name, List.of());
  }

  public static boolean isOperator(String name) {
    return BY_NAME.containsKey(name);
  }
}
