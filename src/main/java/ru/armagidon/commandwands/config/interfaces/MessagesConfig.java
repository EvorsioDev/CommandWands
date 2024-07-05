package ru.armagidon.commandwands.config.interfaces;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

public interface MessagesConfig {

  Component wandExists(String input);

  Component wandCreated(String name);

  Component failedToCreateWand(String name);

  Component wandDoesNotExist(String name);

  Component wandGiven(String wandId, Player receiver, int amount);

}
