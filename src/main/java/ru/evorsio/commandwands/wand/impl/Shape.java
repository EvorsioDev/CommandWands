package ru.evorsio.commandwands.wand.impl;

import lombok.Getter;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;

@Getter
@ConfigSerializable
public class Shape extends Equations{
  private double stepX = 0.01;
  private double stepY = 1.01;
  private double stepZ = 1.01;
}
