package ru.evorsio.commandwands.math.equation;

import java.util.HashMap;
import java.util.Map;
import lombok.Getter;

@Getter
public enum Functions implements Function {

  POWER(2){
    @Override
    public double apply(double... args) {
      return Math.pow(args[0], args[1]);
    }
  },
  LN(1) {
    public double apply(double... args) {
      return Math.log(args[0]);
    }
  },
  SINE(1) {
    @Override
    public double apply(double... args) {
      return Math.sin(args[0]);
    }
  },
  COSINE(1) {
    @Override
    public double apply(double... args) {
      return Math.cos(args[0]);
    }
  },
  TANGENT(1) {
    @Override
    public double apply(double... args) {
      return Math.tan(args[0]);
    }
  },
  COTANGENT(1) {
    @Override
    public double apply(double... args) {
      return (10000 / Math.tan(args[0])) / 10000;
    }
  },
  ABSOLUTE(1) {
    public double apply(double... args) {
      return Math.abs(args[0]);
    }
  };

  private static final Map<String, Functions> BY_FUNCTOR = new HashMap<>();

  private final int arity;

  Functions(int arity) {
    this.arity = arity;
  }

  static {
    BY_FUNCTOR.put("pow", POWER);
    BY_FUNCTOR.put("ln", LN);
    BY_FUNCTOR.put("sin", SINE);
    BY_FUNCTOR.put("cos", COSINE);
    BY_FUNCTOR.put("tg", TANGENT);
    BY_FUNCTOR.put("ctg", COTANGENT);
    BY_FUNCTOR.put("abs", ABSOLUTE);
  }

  public static Functions getByFunctor(String functor) {
    return BY_FUNCTOR.get(functor);
  }


  public static boolean isFunction(String functor) {
    return BY_FUNCTOR.containsKey(functor);
  }
}
