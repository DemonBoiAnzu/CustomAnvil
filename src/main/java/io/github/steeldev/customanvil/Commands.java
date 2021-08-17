package io.github.steeldev.customanvil;

import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.executors.CommandExecutor;

import static io.github.steeldev.customanvil.utils.Util.sendMessage;

public class Commands {
    private static CustomAnvil main = CustomAnvil.getInstance();

    public static void register() {
        CommandExecutor help = (sender, objects) -> {
            sendMessage(sender, false, "&7=====<#3ffefa>CustomAnvil &bCommands&7=====");
            sendMessage(sender, false, "&eHelp &7- Show's this.");
            sendMessage(sender, false, "&eReload &7- Reload configuration.");
        };

        new CommandAPICommand("customanvil").withAliases("ca", "canvil")
                .executes(help)
                .withSubcommand(new CommandAPICommand("reload")
                        .executes((sender, objects) -> {
                            main.config.loadConfigFile();
                            sendMessage(sender, true, "&aSuccessfully reload configuration.");
                        }))
                .withSubcommand(new CommandAPICommand("help")).executes(help).register();
    }
}
