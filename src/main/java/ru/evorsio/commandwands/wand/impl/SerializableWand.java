package ru.evorsio.commandwands.wand.impl;

import lombok.Getter;
import lombok.ToString;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Particle.DustOptions;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import ru.evorsio.commandwands.math.equation.Term;
import ru.evorsio.commandwands.math.parsing.Parser;
import ru.evorsio.commandwands.math.parsing.ParserException;
import ru.evorsio.commandwands.math.parsing.impl.ParserImpl;

@Getter
@ToString
@ConfigSerializable
public class SerializableWand implements ItemStackWand {

  private ItemStack texture = new ItemStack(Material.STICK);
  private Curve curve = new Curve();
  private static final Parser parser = new ParserImpl();

  @Override
  public void use(Player user) {
    Term OX, OY, OZ;
    try {
      OX = parser.parse(curve.getOX());
      OY = parser.parse(curve.getOY());
      OZ = parser.parse(curve.getOZ());
    } catch (ParserException e) {
      System.err.println(e.getMessage());
      return;
    }

    Location OO = user.getLocation().clone();
    for (double t = 0; t <= 1; t += 0.01) {
      double offsetX = OX.compute(t);
      double offsetY = OY.compute(t);
      double offsetZ = OZ.compute(t);
      OO.getWorld().spawnParticle(Particle.REDSTONE,
          OO.clone().add(offsetX, offsetY, offsetZ), 1, 0.1, 0.1,
              0.1, 0, new DustOptions(Color.AQUA, 1));
    }
  }
}
