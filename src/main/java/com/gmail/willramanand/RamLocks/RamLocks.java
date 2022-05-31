package com.gmail.willramanand.RamLocks;

import com.gmail.willramanand.RamLocks.commands.LockCommand;
import com.gmail.willramanand.RamLocks.commands.UnlockCommand;
import com.gmail.willramanand.RamLocks.lang.Lang;
import com.gmail.willramanand.RamLocks.lang.LangConfiguration;
import com.gmail.willramanand.RamLocks.listener.LockListener;
import com.gmail.willramanand.RamLocks.utils.Txt;
import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class RamLocks extends JavaPlugin {

    private static final Logger log = Logger.getLogger("RamLocks");
    private static RamLocks i;
    private LangConfiguration langConfiguration;

    @Override
    public void onEnable() {
        i = this;

        long startTime = System.currentTimeMillis();

        langConfiguration = new LangConfiguration(this);
        langConfiguration.load();

        log.info(Txt.parse(Lang.ENABLE_START));

        // Config
        this.getConfig().options().copyDefaults(true);
        this.saveConfig();

        // Commands
        registerCommands();

        // Listeners
        registerEvents();

        startTime = System.currentTimeMillis() - startTime;
        log.info(Txt.process(Lang.ENABLE_COMPLETE, "{start-time}", String.valueOf(startTime)));
    }

    @Override
    public void onDisable() {
        log.info(Txt.parse(Lang.DISABLE_START));
        log.info(Txt.parse(Lang.DISABLE_COMPLETE));
    }

    public static RamLocks getInstance() {
        return i;
    }

    private void registerCommands() {
        addCommand("lock", new LockCommand(this));
        addCommand("unlock", new UnlockCommand(this));
    }
    
    private void addCommand(String name, CommandExecutor executor) {
        this.getCommand(name).setExecutor(executor);
    }

    private void registerEvents() {
        PluginManager pm = this.getServer().getPluginManager();
        pm.registerEvents(new LockListener(), this);
    }

    public String getLang(Lang lang) { return langConfiguration.get(lang); }
}
