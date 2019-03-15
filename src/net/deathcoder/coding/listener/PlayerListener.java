package net.deathcoder.coding.listener;

import com.mojang.authlib.GameProfile;
import net.deathcoder.coding.DeathCodingPlugin;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static net.deathcoder.coding.DeathHelper.*;

public class PlayerListener implements Listener {
    private final DeathCodingPlugin plugin;

    @EventHandler
    public void onInteract(final PlayerInteractEvent event) {
        if (!isRightClick(event)) return;
        if (event.getClickedBlock() == null || event.getClickedBlock().getType() != Material.WALL_SIGN) return;

        Player player = event.getPlayer();
        Block block = event.getClickedBlock();
        Sign signBlock = (Sign) block.getState();
        Location blockLoc = signBlock.getLocation();

        do {
            runSign(signBlock);
            player.sendMessage("нашёл!");
        } while ((signBlock = getNextSign(signBlock)) != null);
    }

    public PlayerListener(final DeathCodingPlugin plugin) {
        this.plugin = plugin;
    }

    public static class Key {
        private final int i;

        public Key(int i) {
            this.i = i;
        }

        public int getI() {
            return i;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Key key = (Key) o;
            return i == key.i;
        }

        @Override
        public int hashCode() {
            return i;
        }
    }
}
