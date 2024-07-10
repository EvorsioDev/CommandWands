package ru.evorsio.commandwands.wand.impl;

import lombok.Getter;
import lombok.ToString;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;

@Getter
@ToString
@ConfigSerializable
public class Curve {

  private String OX;
  private String OY;
  private String OZ;

  public Curve(String OX, String OY, String OZ) {
    this.OX = OX;
    this.OY = OY;
    this.OZ = OZ;
  }

  Curve() {
    this("1", "1", "1");
  }
}
