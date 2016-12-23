package net.hchayburn.donorreclaim;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class DonatorReclaim extends JavaPlugin implements Listener {

    private static Permission perms;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        getServer().getPluginManager().registerEvents(this, this);
        setupPermissions();
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (player.hasPlayedBefore()) return;
        String group = perms.getPrimaryGroup(player);
        if (getConfig().contains(group)) {
            for (String string : getConfig().getStringList(group)) {
                Bukkit.dispatchCommand(getServer().getConsoleSender(), string.replace("{name}", player.getName()));
            }
        }
    }

    private boolean setupPermissions() {
        RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
        perms = rsp.getProvider();
        return perms != null;
    }
}