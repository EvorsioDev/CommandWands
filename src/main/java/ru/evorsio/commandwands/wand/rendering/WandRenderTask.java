package ru.evorsio.commandwands.wand.rendering;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Particle.DustOptions;
import org.bukkit.util.Vector;
import ru.evorsio.commandwands.wand.impl.Shape;
import ru.evorsio.commandwands.wand.impl.Trajectory;

public class WandRenderTask implements Runnable {

  private double t = 0;

  private final Shape shape;
  private final Trajectory trajectory;
  private final Location O;

  public WandRenderTask(Shape shape, Trajectory trajectory, Location o) {
    this.shape = shape;
    this.trajectory = trajectory;
    O = o;
  }

  @Override
  public void run() {

    Location OO = O.clone().add(trajectory.compute(t));

    for (double t1 = 0; t1 <= 1; t1 += shape.getStepX()) {
      for (double t2 = 0; t2 <= 1; t2 += shape.getStepY()) {
        for (double t3 = 0; t3 <= 1; t3 += shape.getStepY()) {

          Vector shapePoint = shape.compute(t1, t2, t3);
//              double rx = offsetX * Math.cos(yaw) + offsetY * Math.sin(pitch) * Math.sin(yaw)
//                  + offsetZ * Math.cos(pitch) * Math.sin(yaw);
//              double ry = offsetY * Math.cos(pitch) - offsetZ * Math.sin(pitch);
//              double rz = -offsetX * Math.sin(yaw) + offsetY * Math.sin(pitch) * Math.cos(yaw)
//                  + offsetZ * Math.cos(pitch) * Math.cos(yaw);

          OO.getWorld().spawnParticle(Particle.REDSTONE,
              OO.clone().add(shapePoint), 1, 0, 0,
              0, 0, new DustOptions(Color.fromRGB(255, 118, 0), 1));
        }
      }
    }

    t += trajectory.getTrajectoryStep();
  }
}
