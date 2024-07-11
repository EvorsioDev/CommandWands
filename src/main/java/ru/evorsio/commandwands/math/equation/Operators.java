package ru.evorsio.commandwands.math.equation;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import java.util.List;
import lombok.Getter;

@Getter
public enum Operators implements Function {

  ADD(3, 2, "+") {
    @Override
    public double apply(double... args) {
      return args[0] + args[1];
    }
  },
  SUBTRACT(3, 2, "-") {
    @Override
    public double apply(double... args) {
      return args[0] - args[1];
    }
  },
  MULTIPLY(2, 2, "*"){
    @Override
    public double apply(double... args) {
      return args[0] * args[1];
    }
  },
  DIVIDE(2, 2, "/"){
    @Override
    public double apply(double... args) {
      return args[0] / args[1];
    }
  },
  NEGATE(2, 1, "-"){
    @Override
    public double apply(double... args) {
      return -args[0];
    }
  };

  private static final BiMap<String, List<Operators>> BY_NAME = HashBiMap.create();

  private final int precedence;
  private final int arity;
  private final String name;

  Operators(int precedence, int arity, String name) {
    this.precedence = precedence;
    this.arity = arity;
    this.name = name;
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
