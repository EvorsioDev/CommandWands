package ru.evorsio.commandwands.wand.impl;

import java.lang.reflect.Type;
import java.util.stream.Collectors;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;
import org.spongepowered.configurate.serialize.TypeSerializer;
import ru.evorsio.commandwands.math.equation.Expression;
import ru.evorsio.commandwands.math.equation.Functions;
import ru.evorsio.commandwands.math.equation.Literal;
import ru.evorsio.commandwands.math.equation.Operators;
import ru.evorsio.commandwands.math.equation.Parameter;
import ru.evorsio.commandwands.math.equation.Term;

class TermSerializer implements TypeSerializer<Term> {

  @Override
  public Term deserialize(Type type, ConfigurationNode node) throws SerializationException {
    return null;
  }

  @Override
  public void serialize(Type type, @Nullable Term obj, ConfigurationNode node)
      throws SerializationException {
    if (obj == null) {
      node.raw(null);
      return;
    }
    node.set(serialize0(obj));
  }

  private String serialize0(Term term) {
    if (term instanceof Literal literal) {
      return String.valueOf(literal.getValue());
    } else if (term instanceof Parameter parameter) {
      return parameter.getParamName();
    } else if (term instanceof Expression expression) {
      if (expression.getFunction() instanceof Functions func) {
        return String.format("%s(%s)",
            func.getName(),
            expression.getTerms().stream().map(this::serialize0)
                .collect(Collectors.joining(",")));
      } else if (expression.getFunction() instanceof Operators operator) {
        if (operator.getArity() == 2) {
          return "(" + serialize0(expression.getTerms().get(0)) + operator.getName() + serialize0(expression.getTerms().get(1)) + ")";
        } else if (operator.getArity() == 1) {
          return "(" + operator.getName() + serialize0(expression.getTerms().get(0)) + ")";
        } else {
          throw new IllegalArgumentException("This serializer only supports operators up to arity 2");
        }
      } else {
        throw new IllegalArgumentException("This serializer supports only internal function implementations");
      }
    }
    throw new IllegalArgumentException("This serializer supports only internal term implementations");
  }
}
