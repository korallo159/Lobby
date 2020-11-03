package koral.lobby;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.Map;

import static koral.lobby.LobbyCommands.freeze;

public class LobbyListener implements Listener {
    Map<String, Long> timer = new HashMap<>();
    Map<String, BukkitTask> task = new HashMap<>();
    private static BukkitTask taskid;
    Lobby plugin;
    LobbySpawn spawn;


    public LobbyListener(Lobby plugin) {
        this.plugin = plugin;
         spawn = new LobbySpawn(plugin);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        spawn.spawnTeleport(player);
        if (!player.hasPermission("lobby.bypass.kick")) {
            timer.put(player.getUniqueId().toString(), System.currentTimeMillis() / 1000 + plugin.getConfig().getInt("timetokick"));
            kickTimer(player);
        }

        if(plugin.getConfig().getBoolean("disablejoinmessage"))
            e.setJoinMessage("");
    }
   @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e){
            Player p = e.getPlayer();
            clearMapAndTaskState(p);

            if(plugin.getConfig().getBoolean("disablequitmessage"))
            e.setQuitMessage("");
        }
    @EventHandler
    public void playerChatEvent(AsyncPlayerChatEvent e){
        Player p = e.getPlayer();
        if(freeze){
            if(!p.hasPermission("lobby.bypass.chat")) {
                p.sendMessage(ChatColor.RED + "Czat został wyłączony przez administratora, nie możesz wysyłać wiadomości.");
                e.setCancelled(true);
            }
        }
    }



    public void kickTimer(Player player) {
       BukkitTask tid = new BukkitRunnable() {
            @Override
            public void run() {
            if(timer.get(player.getUniqueId().toString()) < System.currentTimeMillis() / 1000) {
                player.kickPlayer("Wybierz jakiś serwer.");
                timer.remove(player.getUniqueId().toString());
                this.cancel();
               }
            }
        }.runTaskTimer(plugin, 0, 20);
       task.put(player.getUniqueId().toString(), tid);
    }
    public void clearMapAndTaskState(Player p){
        if(task.containsKey(p.getUniqueId().toString())) {
            taskid = task.get(p.getUniqueId().toString());
            task.remove(p.getUniqueId().toString());
            taskid.cancel();
        }
        if(timer.containsKey(p.getUniqueId().toString()))
            timer.remove(p.getUniqueId().toString());
    }




}
