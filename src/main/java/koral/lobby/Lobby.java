package koral.lobby;

import org.bukkit.plugin.java.JavaPlugin;
import java.io.File;

public final class Lobby extends JavaPlugin {
    LobbyListener listener;
    LobbySpawn spawn;
    @Override
    public void onEnable() {
    new File(getDataFolder() + File.separator + "config.yml");
    this.saveDefaultConfig();
    this.spawn = new LobbySpawn(this);
    this.spawn.saveLocationFile();
    this.listener = new LobbyListener(this);
    this.getServer().getPluginManager().registerEvents(listener, this);
    this.getServer().getPluginManager().registerEvents(spawn, this);
    getCommand("lobby").setExecutor(new LobbyCommands(this));
    getCommand("setspawn").setExecutor(new LobbySpawn(this));
    getCommand("spawn").setExecutor(new LobbySpawn(this));
    }

    @Override
    public void onDisable() {
    }
}
