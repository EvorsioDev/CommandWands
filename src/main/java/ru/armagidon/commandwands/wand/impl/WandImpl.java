package ru.armagidon.commandwands.wand.impl;

import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;

@Getter
@ConfigSerializable
public class WandImpl implements ItemStackWand {

  private ItemStack texture;

  @Override
  public void use(Player user) {
    // TODO use wand
  }
}
