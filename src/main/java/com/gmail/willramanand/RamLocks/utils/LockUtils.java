package com.gmail.willramanand.RamLocks.utils;

import com.gmail.willramanand.RamLocks.RamLocks;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Container;
import org.bukkit.persistence.PersistentDataType;

import java.util.UUID;

public class LockUtils {

    public static void setLock(Container container, UUID uuid) {
        container.getPersistentDataContainer().set(new NamespacedKey(RamLocks.getInstance(), "lock_key"), PersistentDataType.STRING, uuid.toString());
        container.update(true);
    }

    public static boolean isLocked(Container container) {
        return container.getPersistentDataContainer().has(new NamespacedKey(RamLocks.getInstance(), "lock_key"));
    }

    public static void removeLock(Container container) {
        container.getPersistentDataContainer().remove(new NamespacedKey(RamLocks.getInstance(), "lock_key"));
        container.update(true);
    }

    public static boolean isOwner(Container container, UUID uuid) {
        String lockString = container.getPersistentDataContainer().get(new NamespacedKey(RamLocks.getInstance(), "lock_key"), PersistentDataType.STRING);
        if (lockString == null) return false;
        UUID lockKey = UUID.fromString(lockString);
        return uuid.equals(lockKey);
    }
}
