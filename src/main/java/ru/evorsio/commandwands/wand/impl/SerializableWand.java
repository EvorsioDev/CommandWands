package ru.evorsio.commandwands.wand.impl;

import com.google.inject.Inject;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import lombok.Getter;
import lombok.ToString;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.slf4j.Logger;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import ru.evorsio.commandwands.math.parsing.Parser;
import ru.evorsio.commandwands.math.parsing.impl.ParserImpl;
import ru.evorsio.commandwands.wand.rendering.WandRenderTask;

@Getter
@ToString
@ConfigSerializable
public class SerializableWand implements ItemStackWand {

  private ItemStack texture = new ItemStack(Material.STICK);
  private Shape shape = new Shape();
  private Trajectory trajectory = new Trajectory();

  private static final Parser SHAPE_PARSER = new ParserImpl(Map.of("t1", 0, "t2", 1, "t3", 2));
  private static final Parser TRAJECTORY_PARSER = new ParserImpl();

  private transient final Logger logger;

  private transient final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

  @Inject
  public SerializableWand(Logger logger) {
    this.logger = logger;
  }

  @Override
  public void use(Player user) {

    double pitch = Math.toRadians(-user.getEyeLocation().getPitch());
    double yaw = Math.toRadians(-user.getEyeLocation().getYaw() - 180);
    Location O = user.getEyeLocation();

    long period = 200;
    var future = scheduler.scheduleAtFixedRate(new WandRenderTask(shape, trajectory, O),
        period, period, TimeUnit.MILLISECONDS);
    Runnable canceller = () -> future.cancel(false);
    scheduler.schedule(canceller, period * (long) (1 / trajectory.getTrajectoryStep()),
        TimeUnit.MILLISECONDS);
  }

  @Override
  public void onReload() {
    try {
      shape.load(SHAPE_PARSER);
      trajectory.load(TRAJECTORY_PARSER);
    } catch (Exception e) {
      logger.error("Parsing error: {}", e.getMessage());
    }
  }
}
