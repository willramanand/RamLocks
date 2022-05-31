package com.gmail.willramanand.RamLocks.listener;

import com.gmail.willramanand.RamLocks.lang.Lang;
import com.gmail.willramanand.RamLocks.utils.LockUtils;
import com.gmail.willramanand.RamLocks.utils.Txt;
import org.bukkit.block.Block;
import org.bukkit.block.Container;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.BlockInventoryHolder;
import org.bukkit.inventory.DoubleChestInventory;

public class LockListener implements Listener {

    @EventHandler
    public void preventNonOwnerOpening(InventoryOpenEvent event) {
        if (!(event.getInventory().getHolder() instanceof BlockInventoryHolder holder)) return;
        if (!(holder.getBlock().getState() instanceof Container container)) return;


        if (!(LockUtils.isLocked(container))) return;
        if (LockUtils.isOwner(container, event.getPlayer().getUniqueId())) return;
        event.setCancelled(true);
        event.getPlayer().sendMessage(Txt.parse(Lang.NOT_CONTAINER_OWNER));
    }

    @EventHandler
    public void preventNonOwnerDoubleChest(InventoryOpenEvent event) {
        if (!(event.getInventory() instanceof DoubleChestInventory doubleChest)) return;

        Container left = (Container) doubleChest.getLeftSide().getHolder();
        Container right = (Container) doubleChest.getRightSide().getHolder();

        boolean isLocked;
        boolean isOwned;

        if (left == null || right == null) return;

        isLocked = LockUtils.isLocked(left) || LockUtils.isLocked(right);
        isOwned = LockUtils.isOwner(left, event.getPlayer().getUniqueId()) && LockUtils.isOwner(right, event.getPlayer().getUniqueId());

        if (!isLocked) return;
        if (isOwned) return;

        event.setCancelled(true);
        event.getPlayer().sendMessage(Txt.parse(Lang.NOT_CONTAINER_OWNER));
    }

    @EventHandler
    public void lockedContainerBreaking(BlockBreakEvent event) {
        if (!(event.getBlock().getState() instanceof Container container)) return;

        if (LockUtils.isLocked(container)) {
            if (LockUtils.isOwner(container, event.getPlayer().getUniqueId())) {
                event.getPlayer().sendMessage(Txt.parse(Lang.BROKEN_LOCKED_CONTAINER));
                LockUtils.removeLock(container);
            } else {
                event.setCancelled(true);
                event.getPlayer().sendMessage(Txt.parse(Lang.NOT_CONTAINER_OWNER));
            }
        }
    }

    @EventHandler
    public void preventExplodeBreaking(EntityExplodeEvent event) {
        if (event.blockList().isEmpty()) return;
        for (Block block : event.blockList()) {
            if (block.getState() instanceof Container container) {
                if (LockUtils.isLocked(container)) {
                    event.blockList().remove(block);
                }
            }
        }
    }
}
