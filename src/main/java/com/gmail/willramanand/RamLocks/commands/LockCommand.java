package com.gmail.willramanand.RamLocks.commands;

import com.gmail.willramanand.RamLocks.RamLocks;
import com.gmail.willramanand.RamLocks.lang.Lang;
import com.gmail.willramanand.RamLocks.utils.LockUtils;
import com.gmail.willramanand.RamLocks.utils.Txt;
import org.bukkit.block.Container;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.DoubleChestInventory;
import org.bukkit.inventory.InventoryHolder;

import java.util.ArrayList;
import java.util.List;

public class LockCommand extends LockBaseCommand {

    public LockCommand(RamLocks plugin) {
        super(plugin, true, true, null, 0 , 0);
    }

    @Override
    public void perform(CommandContext context) {
        if (context.player.getTargetBlock(5) == null || !(context.player.getTargetBlock(5).getState() instanceof InventoryHolder holder)) {
            context.msg(Lang.NON_LOCKABLE_BLOCK);
            return;
        }

        if (holder instanceof Container container) {
            if (LockUtils.isLocked(container)) {
                if (LockUtils.isOwner(container, context.player.getUniqueId())) {
                    context.msg(Lang.CANNOT_LOCK_ALREADY_LOCKED_SELF);
                } else {
                    context.msg(Lang.CANNOT_LOCK_ALREADY_LOCKED_OTHER);
                }
                return;
            }
            // Handles double chest
            if (holder.getInventory() instanceof DoubleChestInventory inv) {
                Container leftContain = (Container) inv.getLeftSide().getHolder();
                Container rightContain = (Container) inv.getRightSide().getHolder();

                List<Container> chestContainers = new ArrayList<>();
                chestContainers.add(rightContain);
                chestContainers.add(leftContain);

                for (Container c : chestContainers) {
                    LockUtils.setLock(c, context.player.getUniqueId());
                    context.msg(Txt.process(Lang.LOCKED_CONTAINER, "{x-location}", String.valueOf(c.getLocation().getBlockX()), "{y-location}", String.valueOf(c.getLocation().getBlockY()), "{z-location}", String.valueOf(c.getLocation().getBlockZ())));
                }
            } else { // single block container
                LockUtils.setLock(container, context.player.getUniqueId());
                context.msg(Txt.process(Lang.LOCKED_CONTAINER, "{x-location}", String.valueOf(container.getLocation().getBlockX()), "{y-location}", String.valueOf(container.getLocation().getBlockY()), "{z-location}", String.valueOf(container.getLocation().getBlockZ())));
            }
        }
    }

    @Override
    public List<String> tabCompletes(CommandSender sender, String[] args) {
        return new ArrayList<>();
    }
}
