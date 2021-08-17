package io.github.steeldev.customanvil;

import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.CommandAPIConfig;
import io.github.steeldev.customanvil.listeners.Anvil;
import io.github.steeldev.customanvil.listeners.ResourcePack;
import io.github.steeldev.customanvil.utils.CALogger;
import io.github.steeldev.customanvil.utils.Config;
import io.github.steeldev.customanvil.utils.Util;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class CustomAnvil extends JavaPlugin {

    private static CustomAnvil instance;
    public Logger logger;
    public Config config;

    public static CustomAnvil getInstance() {
        return instance;
    }

    @Override
    public void onLoad() {
        instance = this;
        CommandAPI.onLoad(new CommandAPIConfig().silentLogs(true));
    }

    @Override
    public void onEnable() {
        CommandAPI.onEnable(this);
        config = new Config(this);
        Commands.register();
        Util.log("&aSuccessfully enabled <#3ffefa>CustomAnvil &ev%s&a.".formatted(getDescription().getVersion()));

        Util.registerEvent(new Anvil());
        Util.registerEvent(new ResourcePack());
    }

    @Override
    public void onDisable() {
        Util.log("&aSuccessfully disabled <#3ffefa>CustomAnvil &ev%s&a.".formatted(getDescription().getVersion()));
    }

    @Override
    public Logger getLogger() {
        if (logger == null) logger = CALogger.getLogger();
        return logger;
    }
}
