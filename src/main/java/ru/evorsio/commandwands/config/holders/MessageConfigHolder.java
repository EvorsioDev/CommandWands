package ru.evorsio.commandwands.config.holders;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import ru.evorsio.commandwands.config.interfaces.MessagesConfig;

@ConfigSerializable
public class MessageConfigHolder implements MessagesConfig {

  private Component wandExists = Component.text("Wand %input% exists");
  private Component wandCreated = Component.text("Wand %input% created");
  private Component failedToCreateWand = Component.text("Failed to create wand");
  private Component wandDoesNotExist = Component.text("Wand does not exist");
  private Component wandGiven = Component.text("Wand given");

  @Override
  public Component wandExists(String input) {
    return wandExists;
  }

  @Override
  public Component wandCreated(String name) {
    return wandCreated;
  }

  @Override
  public Component failedToCreateWand(String name) {
    return failedToCreateWand;
  }

  @Override
  public Component wandDoesNotExist(String name) {
    return wandDoesNotExist;
  }

  @Override
  public Component wandGiven(String wandId, Player receiver, int amount) {
    return wandGiven;
  }
}
