package ru.armagidon.commandwands.listeners;

import com.google.inject.Inject;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import ru.armagidon.commandwands.wand.Wand;
import ru.armagidon.commandwands.wand.WandRegistry;

public class WandListener implements Listener {

  private final WandRegistry registry;

  @Inject
  public WandListener(WandRegistry registry) {
    this.registry = registry;
  }

  @EventHandler(ignoreCancelled = true)
  public void onPlayerInteract(PlayerInteractEvent event) {
    if (!event.getAction().isRightClick())
      return;
    if (!event.hasItem())
      return;
    Wand wand = registry.findWand(event.getItem());
    if (wand == null)
      return;
    wand.use(event.getPlayer());
  }
}
