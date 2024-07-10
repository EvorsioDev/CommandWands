package ru.evorsio.commandwands;

import com.google.inject.Guice;
import com.google.inject.Injector;
import java.io.IOException;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.spongepowered.configurate.reference.WatchServiceListener;
import revxrsal.commands.bukkit.BukkitCommandHandler;
import ru.evorsio.commandwands.commands.base.BaseCommands;
import ru.evorsio.commandwands.config.modules.ConfigModule;
import ru.evorsio.commandwands.listeners.WandListener;
import ru.evorsio.commandwands.utils.PermissionMap;
import ru.evorsio.commandwands.utils.modules.LoggerModule;
import ru.evorsio.commandwands.utils.modules.PermissionModule;
import ru.evorsio.commandwands.utils.modules.PluginModule;

public final class CommandWands extends JavaPlugin {



  // Injector
  private final Injector injector;

  public CommandWands() {
    this.injector = Guice.createInjector(
        new LoggerModule(getSLF4JLogger()),
        new ConfigModule(getDataFolder()),
        new PluginModule(this),
        new PermissionModule()
    );
  }

  @Override
  public void onEnable() {
    initCommands();
    initCustomPermissions();
    initListeners();
  }

  @Override
  public void onDisable() {
    try {
      injector.getInstance(WatchServiceListener.class).close();
    } catch (IOException e) {
      System.err.println(e.getMessage());
    }
  }

  private void initCommands() {
    BukkitCommandHandler handler = BukkitCommandHandler.create(this);
    handler.enableAdventure();
    handler.register(injector.getInstance(BaseCommands.class));
    handler.registerBrigadier();
  }

  private void initListeners() {
    getServer().getPluginManager().registerEvents(injector.getInstance(WandListener.class), this);
  }

  private void initCustomPermissions() {
    PermissionMap permissionMap = injector.getInstance(PermissionMap.class);
    Bukkit.getServer().getPluginManager().addPermission(permissionMap.getPermission("use"));
  }

}
