package com.gmail.willramanand.RamLocks.lang;

import com.gmail.willramanand.RamLocks.RamLocks;
import com.gmail.willramanand.RamLocks.utils.Txt;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;


public class LangConfiguration {

    private final RamLocks plugin;

    private final BiMap<Lang, String> languageMap;

    public LangConfiguration(RamLocks plugin) {
        this.plugin = plugin;
        this.languageMap = HashBiMap.create();
    }

    private void setup() {
        File file = new File(plugin.getDataFolder() + "/lang-en.yml");

        if (!file.exists()) {
            try {
                file.createNewFile();
                plugin.getLogger().info(Txt.parse("{green}Created lang-en file!"));

                FileConfiguration config = YamlConfiguration.loadConfiguration(file);

                for (Lang lang : Lang.values()) {
                    config.set(lang.name().toLowerCase(), lang.getDefault());
                }

                try {
                    config.save(file);
                    plugin.getLogger().info(Txt.parse("{s}Added {h}" + Lang.values().length +  " {s}new entries to lang-en file!"));
                    load();
                } catch (IOException e) {
                    plugin.getLogger().info(Txt.parse("{w}Could not save lang-en file!"));
                }

            } catch (IOException e) {
                plugin.getLogger().info(Txt.parse("{w}Could not create lang-en file!"));
            }
        }
    }

    public void load() {
        File file = new File(plugin.getDataFolder() + "/lang-en.yml");

        if (file.exists()) {
            FileConfiguration config = YamlConfiguration.loadConfiguration(file);

            int newEntries = 0;
            for (Lang lang : Lang.values()) {
                String message = config.getString(lang.name().toLowerCase());

                if (message == null || message.isEmpty()) {
                    config.set(lang.name().toLowerCase(), lang.getDefault());;
                    message = lang.getDefault();
                    newEntries++;
                }
                languageMap.put(lang, message);
            }

            if (newEntries > 0) {
                try {
                    config.save(file);
                    plugin.getLogger().info(Txt.parse("{s}Added {h}" + newEntries +  " {s}new entries to lang-en file!"));
                } catch (IOException e) {
                    plugin.getLogger().info(Txt.parse("{w}Could not save lang-en file!"));
                }
            }
        } else {
            setup();
        }
    }

    public String get(Lang lang) {
        String message = "";

        if (languageMap.get(lang) == null) {
            message = lang.getDefault();
        } else {
            message = languageMap.get(lang);
        }

        return message;
    }
}
