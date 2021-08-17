package io.github.steeldev.customanvil.listeners;

import io.github.steeldev.customanvil.CustomAnvil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerResourcePackStatusEvent;

import static io.github.steeldev.customanvil.utils.Util.colorize;

public class ResourcePack implements Listener {
    private CustomAnvil main = CustomAnvil.getInstance();

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (main.config.ENABLE_RESOURCE_PACK) {
            player.setResourcePack(main.config.RESOURCE_PACK_URL);
        }
    }

    @EventHandler
    public void resourcePackStatus(PlayerResourcePackStatusEvent event) {
        if (!main.config.ENABLE_RESOURCE_PACK
                || main.config.RESOURCE_PACK_URL.isEmpty()) return;
        Player player = event.getPlayer();
        switch (event.getStatus()) {
            case DECLINED:
            case FAILED_DOWNLOAD:
                if (main.config.FORCE_RESOURCE_PACK)
                    Bukkit.getScheduler().scheduleSyncDelayedTask(main, () -> player.kickPlayer(colorize("&cYou must accept the servers resource pack.")), 10l);
                break;
        }
    }
}
