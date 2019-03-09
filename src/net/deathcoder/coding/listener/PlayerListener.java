package net.deathcoder.coding.listener;

import net.deathcoder.coding.DeathCodingPlugin;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import static net.deathcoder.coding.DeathHelper.*;

public class PlayerListener implements Listener {
    private final DeathCodingPlugin plugin;

    @EventHandler
    public void onInteract(final PlayerInteractEvent event) {
        if (!isRightClick(event)) return;
        if (event.getClickedBlock().getType() != Material.WALL_SIGN || event.getClickedBlock() == null) return;

        final Player player = event.getPlayer();
        final Block block = event.getClickedBlock();
        Sign signBlock = (Sign) block.getState();
        final Location blockLoc = signBlock.getLocation();

        do {
            //run(signBlock);
        } while ((signBlock = getNextSign(signBlock)) != null);

        /* for (final Block fBlock : getBlocks(block, 2)) {
            if (fBlock.getType().equals(Material.WALL_SIGN) && !fBlock.getLocation().equals(blockLoc)) player.sendMessage("Я нашёл табличку! Координаты: " + fBlock.getLocation());
        } */

    }

    public PlayerListener(final DeathCodingPlugin plugin) {
        this.plugin = plugin;
    }
}
