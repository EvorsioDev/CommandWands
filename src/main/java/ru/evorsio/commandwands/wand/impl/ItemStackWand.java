package ru.evorsio.commandwands.wand.impl;

import org.bukkit.inventory.ItemStack;
import ru.armagidon.advcolo.loader.ReloadableConfig;
import ru.evorsio.commandwands.wand.Wand;

public interface ItemStackWand extends Wand, ReloadableConfig {

  ItemStack getTexture();

}
