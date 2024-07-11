package ru.evorsio.commandwands.math.parsing.impl;

import com.google.common.collect.ImmutableMap;
import java.util.Map;
import java.util.Map.Entry;
import ru.evorsio.commandwands.math.equation.Functions;
import ru.evorsio.commandwands.math.equation.Operators;

public class ParsingContext {

  private final Map<String, Integer> parameters;

  public ParsingContext(Map<String, Integer> parameters) {
    this.parameters = parameters;
    validateParameterNames(parameters);
  }

  private void validateParameterNames(Map<String, Integer> parameters) {
    for (Entry<String, Integer> entry : parameters.entrySet()) {
      if (entry.getValue() < 0)
        throw new IllegalArgumentException("Parameter index must be non-negative");
      else if (entry.getValue() >= parameters.size())
        throw new IllegalArgumentException("Parameter index must be less parameter count");
      else if (Functions.isFunction(entry.getKey()))
        throw new IllegalArgumentException("Parameter cannot have the same name as predefined function");
      else if (Operators.isOperator(entry.getKey()))
        throw new IllegalArgumentException("Parameter cannot have the same name as predefined operator");
    }
  }

  public Map<String, Integer> getParameters() {
    return ImmutableMap.copyOf(parameters);
  }
}
