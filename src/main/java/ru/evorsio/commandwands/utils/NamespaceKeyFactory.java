package ru.evorsio.commandwands.utils;

import com.google.inject.Inject;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.Plugin;

public class NamespaceKeyFactory {

  private final Map<String, NamespacedKey> keys = new HashMap<>();
  private final Plugin plugin;

  @Inject
  public NamespaceKeyFactory(Plugin plugin) {
    this.plugin = plugin;
  }

  public NamespacedKey getKey(String key) {
    return keys.computeIfAbsent(key, k -> new NamespacedKey(plugin, k));
  }

}
