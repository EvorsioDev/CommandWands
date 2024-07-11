package ru.evorsio.commandwands.wand.impl;

import lombok.Getter;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;

@Getter
@ConfigSerializable
public class Trajectory extends Equations {

  private double trajectoryStep = 0.01;

}
