package ru.evorsio.commandwands.math.parsing.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EmptyStackException;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Stack;
import ru.evorsio.commandwands.math.equation.Expression;
import ru.evorsio.commandwands.math.equation.Function;
import ru.evorsio.commandwands.math.equation.Functions;
import ru.evorsio.commandwands.math.equation.Literal;
import ru.evorsio.commandwands.math.equation.Operators;
import ru.evorsio.commandwands.math.equation.Parameter;
import ru.evorsio.commandwands.math.equation.Term;
import ru.evorsio.commandwands.math.parsing.Parser;
import ru.evorsio.commandwands.math.parsing.ParserException;
import ru.evorsio.commandwands.math.parsing.Token;
import ru.evorsio.commandwands.math.parsing.TokenType;

// TODO
// Unary minus fix
// Function names fix
// Code cleanup
public class ParserImpl implements Parser {


  private static final Map<String, Double> CONSTANTS = Map.of(
      "PI", Math.PI,
      "E", Math.E
  );

  private static final String PARAMETER_NAME = "t";

  public Term parse(String input) throws ParserException {
    Stack<Term> output = new Stack<>();
    Stack<Operator> opStack = new Stack<>();
    Tape tape = new Tape(input);
    while (!tape.isDone()) {
      Token token;
      if (tape.getRegion().isBlank()) {
        tape.skip();
        continue;
      } else if (tape.isSymbol()) {
        token = tape.readSymbol();
      } else if (Character.isDigit(input.charAt(tape.getStart()))) {
        token = tape.readNumber();
      } else if (Character.isLetter(input.charAt(tape.getStart()))) {
        token = tape.readString();
      } else {
        throw new ParserException("Unrecognized token", tape.getStart(), tape.getEnd(), input);
      }
      if (token.getType() == TokenType.NUMBER) {
        output.add(new Literal(Double.parseDouble(token.getValue())));
      } else if (Functions.isFunction(token.getValue())) {
        opStack.push(new Operator(token, Functions.getByFunctor(token.getValue())));
      } else if (Operators.isOperator(token.getValue())) {
        Operators operator = guessOperator(token);
        if (operator == null) {
          throw new ParserException("Wrong use of operator" + token.getValue(), token);
        }
        while (!opStack.isEmpty()) {
          if (opStack.peek().token().getType() == TokenType.PAREN_LEFT) {
            break;
          }
          Function top = opStack.peek().function();
          if (top instanceof Operators op) {
            if (op.getPrecedence() >= operator.getPrecedence())
              doOperator(opStack.pop(), output);
            else
              break;
          } else if (top instanceof Functions) {
            doFunction(opStack.pop(), output);
          } else {
            throw new ParserException("Unexpected token", token);
          }
        }
        opStack.push(new Operator(token, operator));
      } else if (token.getType() == TokenType.QUALIFIER) {
        if (token.getValue().equals(PARAMETER_NAME)) {
          output.push(new Parameter());
        } else if (CONSTANTS.containsKey(token.getValue())) {
          output.push(new Literal(CONSTANTS.get(token.getValue())));
        } else {
          throw new ParserException("Unrecognized token", token);
        }
      } else if (token.getType() == TokenType.PAREN_LEFT) {
        if (token.getPrevious() != null) {
          if (token.getPrevious().getType() == TokenType.QUALIFIER) {
            if (opStack.isEmpty())
              throw new ParserException("Illegal parentheses", token);
            Operator operator = opStack.peek();
            if (operator.function() instanceof Functions) {
              if (!operator.token().equals(token.getPrevious()))
                throw new ParserException("Illegal parentheses", token);
            } else {
              throw new ParserException("Illegal parentheses", token);
            }
          }
        }
        opStack.push(new Operator(token, null));
      } else if (token.getType() == TokenType.PAREN_RIGHT) {
        try {
          while (opStack.peek().token().getType() != TokenType.PAREN_LEFT){
            handleOperator(opStack, output);
          }
        } catch (EmptyStackException e){
          throw new ParserException("Mismatched parenthesis", token);
        }
        opStack.pop();
        if (!opStack.isEmpty() && opStack.peek().function() instanceof Functions) {
          doFunction(opStack.pop(), output);
        }
      } else if (token.getType() == TokenType.COMMA) {
        try {
          while (opStack.peek().token().getType() != TokenType.PAREN_LEFT){
            handleOperator(opStack, output);
          }
        } catch (EmptyStackException e){
          throw new ParserException("Illegal comma", token);
        }
      }
    }

    while (!opStack.isEmpty()) {
      Operator operator = opStack.peek();
      if (operator.token().getType() == TokenType.PAREN_LEFT) {
        throw new ParserException("Mismatched parentheses", operator.token());
      }
      handleOperator(opStack, output);
    }
    if (output.size() > 1) {
      throw new ParserException("Something happened", 0, input.length() - 1, input);
    }

    return output.pop();
  }

  private Operators guessOperator(Token token) {
    int arity;
    if (token.getPrevious() == null) {
      arity = 1;
    } else {
      switch (token.getPrevious().getType()) {
        case NUMBER, PAREN_RIGHT, QUALIFIER:
          arity = 2;
          break;
        case PAREN_LEFT, COMMA:
          arity = 1;
          break;
        case PLUS, SLASH, ASTERISK, MINUS:
          return null;
        default:
          return null;
      }
      ;
    }
    for (Operators candidate : Operators.getByName(token.getValue())) {
      if (candidate.getArity() == arity) {
        return candidate;
      }
    }
    return null;
  }

  private void doOperator(Operator operator, Stack<Term> output) throws ParserException {
    List<Term> args = new ArrayList<>();
    for (int i = 0; i < operator.function().getArity(); i++) {
      if (output.isEmpty()) {
        throw new ParserException(
            "Not enough operands for operator " + operator.token().getValue(), operator.token());
      }
      args.add(output.pop());
    }

    Collections.reverse(args);
    output.push(new Expression(args, operator.function()));
  }

  private void doFunction(Operator operator, Stack<Term> output) throws ParserException {
    List<Term> args = new ArrayList<>();
    for (int i = 0; i < operator.function().getArity(); i++) {
      if (output.isEmpty()) {
        throw new ParserException(
            "Not enough arguments for function " + operator.token().getValue(), operator.token());
      }
      args.add(output.pop());
    }
    Collections.reverse(args);
    output.push(new Expression(args, operator.function()));
  }

  private void handleOperator(Stack<Operator> opStack, Stack<Term> output) throws ParserException {
    Operator top = opStack.peek();
    if (top.function() instanceof Operators) {
      doOperator(opStack.pop(), output);
    } else if (top.function() instanceof Functions) {
      doFunction(opStack.pop(), output);
    } else {
      throw new ParserException("Unexpected token", top.token());
    }
  }
}
