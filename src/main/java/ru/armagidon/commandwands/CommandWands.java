package ru.armagidon.commandwands;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import revxrsal.commands.bukkit.BukkitCommandHandler;
import ru.armagidon.commandwands.commands.base.BaseCommands;
import ru.armagidon.commandwands.config.modules.ConfigModule;
import ru.armagidon.commandwands.listeners.WandListener;
import ru.armagidon.commandwands.utils.PermissionMap;
import ru.armagidon.commandwands.utils.modules.LoggerModule;
import ru.armagidon.commandwands.utils.modules.PermissionModule;
import ru.armagidon.commandwands.utils.modules.PluginModule;

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
