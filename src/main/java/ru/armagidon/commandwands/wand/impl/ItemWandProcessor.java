package ru.armagidon.commandwands.wand.impl;

import com.google.inject.Inject;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import ru.armagidon.commandwands.utils.NamespaceKeyFactory;
import ru.armagidon.commandwands.wand.Wand;

public class ItemWandProcessor {


  private final NamespacedKey wandKey;

  @Inject
  public ItemWandProcessor(NamespaceKeyFactory namespaceKeyFactory) {
    this.wandKey = namespaceKeyFactory.getKey("wand-id");
  }

  public String extract(ItemStack item) {
    if (!item.hasItemMeta())
      return "";
    ItemMeta meta = item.getItemMeta();
    var container = meta.getPersistentDataContainer();
    if (!container.has(wandKey, PersistentDataType.STRING))
      return "";

    return container.get(wandKey, PersistentDataType.STRING);
  }
  
  public void burn(ItemStack item, String id) {
    if (!item.hasItemMeta())
      return;
    ItemMeta meta = item.getItemMeta();
    var container = meta.getPersistentDataContainer();
    if (container.has(wandKey, PersistentDataType.STRING))
      return;

    container.set(wandKey, PersistentDataType.STRING, id);
  }

}
