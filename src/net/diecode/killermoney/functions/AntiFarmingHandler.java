package net.diecode.killermoney.functions;

import net.diecode.killermoney.BukkitMain;
import net.diecode.killermoney.configs.DefaultConfig;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class AntiFarmingHandler implements Listener {

    private static ArrayList<UUID> spawnedEntities = new ArrayList<UUID>();

    @EventHandler
    public void onCreatureSpawn(CreatureSpawnEvent event) {
        if (event.getSpawnReason() == CreatureSpawnEvent.SpawnReason.SPAWNER) {
            if (DefaultConfig.isAntiFarmingSpawner()) {
                spawnedEntities.add(event.getEntity().getUniqueId());

                return;
            }
        }

        if (event.getSpawnReason() == CreatureSpawnEvent.SpawnReason.SPAWNER_EGG) {
            if (DefaultConfig.isAntiFarmingSpawnerEgg()) {
                spawnedEntities.add(event.getEntity().getUniqueId());

                return;
            }
        }
    }

    @EventHandler
    public void onPlayerDeathEvent(PlayerDeathEvent event) {
        if(event.getEntity() instanceof Player && event.getEntity().getKiller() instanceof Player) {
            Player target = event.getEntity();
            Player killer = event.getEntity().getKiller();

            PlayerUtils.getInstance().setMeta(target, killer.getName(), "");
            new BukkitRunnable() {

                @Override
                public void run() {
                    PlayerUtils.getInstance().clearMeta(target, killer.getName());
                }
            }.runTaskLater(BukkitMain.getInstance(), 6000L);
        }
    }

    public static ArrayList<UUID> getSpawnedEntities() {
        return spawnedEntities;
    }
}
