package io.github.steeldev.customanvil.utils;

import io.github.steeldev.customanvil.CustomAnvil;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.event.Listener;

import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Util {
    static CustomAnvil main;

    public static String colorize(String string) {
        Pattern HEX_PATTERN = Pattern.compile("<#([A-Fa-f0-9]){6}>");
        Matcher matcher = HEX_PATTERN.matcher(string);
        while (matcher.find()) {
            final ChatColor hexColor = ChatColor.of(matcher.group().substring(1, matcher.group().length() - 1));
            final String before = string.substring(0, matcher.start());
            final String after = string.substring(matcher.end());
            string = before + hexColor + after;
            matcher = HEX_PATTERN.matcher(string);
        }
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    public static void sendMessage(CommandSender receiver, boolean prefix, String msg) {
        var pref = (prefix) ? "<#3ffefa>CustomAnvil &8> &r" : "";
        if (receiver == null || receiver instanceof ConsoleCommandSender) {
            log((pref + msg));
        } else {
            send(receiver, (pref + msg));
        }
    }

    private static void send(CommandSender receiver, String msg) {
        receiver.sendMessage(colorize(msg));
    }


    public static void log(String msg) {
        log(Level.INFO, msg);
    }

    public static void log(Level level, String msg) {
        if (main == null) main = CustomAnvil.getInstance();
        Bukkit.getConsoleSender().sendMessage(colorize("&7[<#3ffefa>CustomAnvil]%s %s".formatted(
                (!level.equals(Level.INFO)) ? " %s".formatted(level.toString()) : "",
                msg)));
    }

    public static void registerEvent(Listener listener) {
        main.getServer().getPluginManager().registerEvents(listener, main);
    }
}
