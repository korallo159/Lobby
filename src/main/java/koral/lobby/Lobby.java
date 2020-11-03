package koral.lobby;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.time.Period;
import java.util.HashMap;
import java.util.Map;

public final class Lobby extends JavaPlugin {
    LobbyListener listener;

    @Override
    public void onEnable() {
    new File(getDataFolder() + File.separator + "config.yml");
    this.saveDefaultConfig();
    this.listener = new LobbyListener(this);
    this.getServer().getPluginManager().registerEvents(listener, this);
    getCommand("lobby").setExecutor(new LobbyCommands(this));
        // Plugin startup logic
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
//todo zrobic mape i runnable, przy wejsciu przydzielamy miliseconds.
// w runabble sprawdzamy co iles sekund, czy czas jset wiekszy od 5 minut. Jesli ma permisje, to nie liczy.
}
