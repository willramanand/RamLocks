package com.gmail.willramanand.RamLocks.commands;

import com.gmail.willramanand.RamLocks.RamLocks;
import com.gmail.willramanand.RamLocks.lang.Lang;
import com.gmail.willramanand.RamLocks.utils.LockUtils;
import com.gmail.willramanand.RamLocks.utils.Txt;
import org.bukkit.block.Container;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.DoubleChestInventory;

import java.util.ArrayList;
import java.util.List;

public class UnlockCommand extends LockBaseCommand {

    public UnlockCommand(RamLocks plugin) {
        super(plugin, true, true, null, 0, 0);
    }

    @Override
    public void perform(CommandContext context) {
        if (context.player.getTargetBlock(5) == null || !(context.player.getTargetBlock(5).getState() instanceof Container container)) {
            context.msg(Lang.NON_LOCKABLE_BLOCK);
            return;
        }

        if (!LockUtils.isLocked(container)) {
            context.msg(Lang.CANNOT_UNLOCK_NOT_LOCKED);
            return;
        }

        if (LockUtils.isOwner(container, context.player.getUniqueId())) {

            if (container.getInventory() instanceof DoubleChestInventory inv) {
                Container leftContain = (Container) inv.getLeftSide().getHolder();
                Container rightContain = (Container) inv.getRightSide().getHolder();

                List<Container> chestContainers = new ArrayList<>();
                chestContainers.add(rightContain);
                chestContainers.add(leftContain);

                for (Container c : chestContainers) {
                    LockUtils.removeLock(c);
                    context.msg(Txt.process(Lang.UNLOCKED_CONTAINER, "{x-location}", String.valueOf(c.getLocation().getBlockX()), "{y-location}", String.valueOf(c.getLocation().getBlockY()), "{z-location}", String.valueOf(c.getLocation().getBlockZ())));
                }
            } else {
                LockUtils.removeLock(container);
                context.msg(Txt.process(Lang.UNLOCKED_CONTAINER, "{x-location}", String.valueOf(container.getLocation().getBlockX()), "{y-location}", String.valueOf(container.getLocation().getBlockY()), "{z-location}", String.valueOf(container.getLocation().getBlockZ())));
            }
        } else {
            context.msg(Lang.CANNOT_UNLOCK_ALREADY_LOCKED_OTHER);
        }
    }

    @Override
    public List<String> tabCompletes(CommandSender sender, String[] args) {
        return new ArrayList<>();
    }
}
