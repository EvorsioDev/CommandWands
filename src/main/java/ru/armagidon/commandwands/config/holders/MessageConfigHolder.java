package ru.armagidon.commandwands.config.holders;

import net.kyori.adventure.text.Component;
import ru.armagidon.commandwands.config.interfaces.MessagesConfig;

public class MessageConfigHolder implements MessagesConfig {

  @Override
  public Component wandExists(String input) {
    return null;
  }
}
