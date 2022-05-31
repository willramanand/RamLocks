package com.gmail.willramanand.RamLocks.commands;

import com.gmail.willramanand.RamLocks.RamLocks;
import com.gmail.willramanand.RamLocks.lang.Lang;
import com.gmail.willramanand.RamLocks.utils.Txt;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class LockBaseCommand implements TabExecutor {

    protected final RamLocks plugin;

    public List<String> aliases;

    private final String permission;

    public int requiredArgsSize;
    public int totalArgs;

    private final boolean enabled;
    private final boolean playerOnly;

    public String helpText;

    public List<LockBaseCommand> subCommands;


    public LockBaseCommand(RamLocks plugin, boolean enabled, boolean playerOnly, int requiredArgsSize, int totalArgs) {
        this.plugin = plugin;
        this.enabled = enabled;
        this.playerOnly = playerOnly;
        this.permission = null;
        this.requiredArgsSize = requiredArgsSize;
        this.totalArgs = totalArgs;
        this.aliases = new ArrayList<>();
        this.subCommands = new ArrayList<>();
    }

    public LockBaseCommand(RamLocks plugin, boolean enabled, boolean playerOnly, String permission, int requiredArgsSize, int totalArgs) {
        this.plugin = plugin;
        this.enabled = enabled;
        this.playerOnly = playerOnly;
        this.permission = permission;
        this.requiredArgsSize = requiredArgsSize;
        this.totalArgs = totalArgs;
        this.aliases = new ArrayList<>();
        this.subCommands = new ArrayList<>();
    }


    public abstract void perform(CommandContext context);

    public abstract List<String> tabCompletes(CommandSender sender, String[] args);

    public void execute(CommandContext context) {
        // Is there a matching sub command?
        if (context.args.size() > 0) {
            for (LockBaseCommand subCommand : this.subCommands) {
                if (subCommand.aliases.contains(context.args.get(0).toLowerCase())) {
                    context.args.remove(0);
                    context.commandChain.add(this);
                    subCommand.execute(context);
                    return;
                }
            }
        }

        if (!validCall(context)) {
            return;
        }

        if (!this.isEnabled()) {
            context.msg(Lang.COMMAND_DISABLED);
            return;
        }

        perform(context);
    }

    public boolean validCall(CommandContext context) {
        if (requiredArgsSize != 0 && requiredArgsSize > context.args.size()) {
            context.msg(Txt.process(Lang.COMMAND_USAGE , "{usage", context.command.getUsage().replace("<command>", context.command.getName())));
            return false;
        }

        if (totalArgs != -1 && totalArgs < context.args.size()) {
            context.msg(Txt.process(Lang.COMMAND_TOO_MANY_ARGS, "{usage}", context.command.getUsage().replace("<command>", context.command.getName())));
            return false;
        }

        if (!(context.sender instanceof Player) && playerOnly) {
            context.msg(Lang.COMMAND_PLAYER_ONLY);
            return false;
        }

        // Check our perms
        if (permission != null && !(context.sender.hasPermission(permission))) {
            context.msg(Lang.COMMAND_NO_PERMISSION);
            return false;
        }

        // Check spigot perms
        if (context.command.getPermission() != null && !(context.sender.hasPermission(context.command.getPermission()))) {
            context.msg(Lang.COMMAND_NO_PERMISSION);
            return false;
        }

        return true;
    }

    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        this.execute(new CommandContext(sender, command, new ArrayList<>(Arrays.asList(args)), label));
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        return tabCompletes(sender, args);
    }

    public List<String> tabCompletePlayers() {
        List<String> playerNames = new ArrayList<>();
        for (Player player : Bukkit.getOnlinePlayers()) playerNames.add(player.getName());
        return playerNames;
    }

    public List<String> tabCompleteWorlds() {
        List<String> worldNames = new ArrayList<>();
        for (World world : Bukkit.getWorlds()) worldNames.add(world.getName());
        return worldNames;
    }

    public String getHelpText() {
        return helpText;
    }

    public String getPermission() { return permission; }
}
