package ru.evorsio.commandwands.wand.impl;

import lombok.Getter;
import lombok.ToString;
import org.bukkit.util.Vector;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import ru.evorsio.commandwands.math.equation.Term;
import ru.evorsio.commandwands.math.parsing.Parser;
import ru.evorsio.commandwands.math.parsing.ParserException;

@ToString
@ConfigSerializable
class Equations {

  private String OX;
  private String OY;
  private String OZ;

  @Getter
  private transient Term OXTerm, OYTerm, OZTerm;

  public Equations(String OX, String OY, String OZ) {
    this.OX = OX;
    this.OY = OY;
    this.OZ = OZ;
  }

  Equations() {
    this("1", "1", "1");
  }

  public Vector compute(double... t) {
    return new Vector(getOXTerm().compute(t), getOYTerm().compute(t), getOZTerm().compute(t));
  }

  public void load(Parser parser) throws ParserException {
    OXTerm = parser.parse(OX);
    OYTerm = parser.parse(OY);
    OZTerm = parser.parse(OZ);
  }
}
