package ru.armagidon.commandwands.utils;

import java.util.HashMap;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.permissions.Permission;

public class PermissionMap {

  private final Map<String, Permission> permissionMap = new HashMap<>();
  private final String namespace;

  public PermissionMap(String namespace) {
    this.namespace = namespace.toLowerCase();
  }

  public Permission getPermission(String permission) {
    return permissionMap.computeIfAbsent(permission, this::createPermission);
  }

  private Permission createPermission(String name) {
    return new Permission(String.join(".", namespace, name));
  }

}
