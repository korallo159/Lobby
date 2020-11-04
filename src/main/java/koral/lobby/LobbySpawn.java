package koral.lobby;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.File;
import java.io.IOException;

public class LobbySpawn implements Listener, CommandExecutor {
   public static File LocationFile;
   public static YamlConfiguration Location;
    Lobby plugin;
    public LobbySpawn(Lobby plugin) {
        this.plugin = plugin;
        this.LocationFile = new File(this.plugin.getDataFolder(), "Location.yml");
        this.Location = YamlConfiguration.loadConfiguration(this.LocationFile);
    }


    void spawnTeleport(Player player) {
        final double x = this.Location.getDouble("Location." + ".X");
        final double y = this.Location.getDouble("Location." + ".Y");
        final double z = this.Location.getDouble("Location." + ".Z");
        final float yaw = (float) this.Location.getLong("Location." + ".yaw");
        final float pitch = (float) this.Location.getLong("Location" + ".pitch");
        final World world = Bukkit.getWorld(this.Location.getString("Location." + ".worldname"));
        final org.bukkit.Location spawn = new Location(world, x, y, z, yaw, pitch);
        player.teleport(spawn);
    }

    @EventHandler
    public void onPlayerDamageByVoid(final EntityDamageEvent event) {
        if (event.getCause().equals(EntityDamageEvent.DamageCause.VOID) && event.getEntity() instanceof Player) {
            event.setCancelled(true);
            final Player player = (Player) event.getEntity();
            player.sendMessage(ChatColor.GRAY + "Przeteleportowano na spawn, ponieważ spadłeś w otchłań.");
            spawnTeleport(player);
        }
    }


    @Override
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        final Player player = (Player) sender;
        if (label.equalsIgnoreCase("setspawn") && args.length == 0) {
            final double x = player.getLocation().getX();
            final double y = player.getLocation().getY();
            final double z = player.getLocation().getZ();
            final float yaw = player.getLocation().getYaw();
            final float pitch = player.getLocation().getPitch();
            final String worldName = player.getWorld().getName();
            this.Location.set("Location." + ".X", x);
            this.Location.set("Location." + ".Y", y);
            this.Location.set("Location." + ".Z", z);
            this.Location.set("Location." + ".yaw", yaw);
            this.Location.set("Location." + ".pitch", pitch);
            this.Location.set("Location." + ".worldname", worldName);
            this.saveLocationFile();
            player.sendMessage(ChatColor.GRAY + "Ustawiono " + ChatColor.RED + "forcespawn" + ChatColor.GRAY + " w miejscu w którym aktualnie się znajdujesz");
        }


        if (cmd.getName().equalsIgnoreCase("spawn") && args.length == 0) {
            if (LocationFile.exists() && LocationFile != null) {
                spawnTeleport(player);
            } else {
                player.sendMessage(ChatColor.RED + "SPAWN NIE JEST USTAWIONY");
                return true;
            }

        }
        return true;
    }



    void saveLocationFile() {
        try {
            this.Location.save(this.LocationFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}