package ru.armagidon.commandwands.commands.base;

import com.google.inject.Inject;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import revxrsal.commands.annotation.Command;
import revxrsal.commands.annotation.Default;
import revxrsal.commands.annotation.Range;
import revxrsal.commands.bukkit.annotation.CommandPermission;
import ru.armagidon.commandwands.config.interfaces.MessagesConfig;
import ru.armagidon.commandwands.wand.WandRegistry;

public class BaseCommands {

  private final MessagesConfig messagesConfig;
  private final WandRegistry registry;

  @Inject
  public BaseCommands(MessagesConfig messagesConfig, WandRegistry registry) {
    this.messagesConfig = messagesConfig;
    this.registry = registry;
  }

  @Command("wands generate")
  @CommandPermission("commandwands.generate")
  public void generateWand(CommandSender sender, String name) {
    if (registry.wandExists(name)) {
      sender.sendMessage(messagesConfig.wandExists(name));
      return;
    }
    if (registry.createWand(name)) {
      sender.sendMessage(messagesConfig.wandCreated(name));
    } else {
      sender.sendMessage(messagesConfig.failedToCreateWand(name));
    }
  }

  @Command("wands give")
  @CommandPermission("commandwands.give")
  public void giveWand(Player sender, String name, @Default("1") @Range(min = 1) int amount) {
    if (!registry.wandExists(name)) {
      sender.sendMessage(messagesConfig.wandDoesNotExist(name));
      return;
    }
    registry.giveWand(name, sender, amount);
    sender.sendMessage(messagesConfig.wandGiven(name, sender, amount));
  }

}
