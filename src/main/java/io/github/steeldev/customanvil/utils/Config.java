package io.github.steeldev.customanvil.utils;

import io.github.steeldev.customanvil.CustomAnvil;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Config {
    private static FileConfiguration config;
    private static File configFile;
    // Config stuff
    public boolean ADD_LEVELS_LITERAL;
    public boolean ENABLE_RESOURCE_PACK;
    public boolean FORCE_RESOURCE_PACK;
    public String RESOURCE_PACK_URL;
    private CustomAnvil main;


    public Config(CustomAnvil main) {
        this.main = main;
        loadConfigFile();
    }

    public static void setString(String path, String value) throws IOException {
        config.set(path, value);

        config.save(configFile);
    }

    public static void setBool(String path, boolean value) throws IOException {
        config.set(path, value);

        config.save(configFile);
    }

    public void loadConfigFile() {
        if (configFile == null) {
            configFile = new File(main.getDataFolder(), "config.yml");
        }
        if (!configFile.exists()) {
            main.saveResource("config.yml", false);
        }
        config = YamlConfiguration.loadConfiguration(configFile);
        matchConfig();
        loadValues();
    }

    // Used to update config
    @SuppressWarnings("ConstantConditions")
    private void matchConfig() {
        try {
            boolean hasUpdated = false;
            InputStream stream = main.getResource(configFile.getName());
            assert stream != null;
            InputStreamReader is = new InputStreamReader(stream);
            YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(is);
            for (String key : defConfig.getConfigurationSection("").getKeys(true)) {
                if (!config.contains(key)) {
                    config.set(key, defConfig.get(key));
                    hasUpdated = true;
                }
            }
            for (String key : config.getConfigurationSection("").getKeys(true)) {
                if (!defConfig.contains(key)) {
                    config.set(key, null);
                    hasUpdated = true;
                }
            }
            if (hasUpdated)
                config.save(configFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadValues() {
        ADD_LEVELS_LITERAL = config.getBoolean("addLevelsLiteral");
        ENABLE_RESOURCE_PACK = config.getBoolean("enableResourcePack");
        RESOURCE_PACK_URL = config.getString("resourcePackURL");
        FORCE_RESOURCE_PACK = config.getBoolean("forceResourcePack");
    }
}
