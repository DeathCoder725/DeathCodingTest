package net.deathcoder.coding;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.ArrayList;
import java.util.Objects;

public class DeathHelper {
    public static final boolean isRightClick(final PlayerInteractEvent $receiver) {
        return Objects.equals($receiver.getAction(), Action.RIGHT_CLICK_BLOCK) || Objects.equals($receiver.getAction(), Action.RIGHT_CLICK_AIR);
    }

    public static final boolean isLeftClick(final PlayerInteractEvent $receiver) {
        return Objects.equals($receiver.getAction(), Action.LEFT_CLICK_BLOCK) || Objects.equals($receiver.getAction(), Action.LEFT_CLICK_AIR);
    }

    public static ArrayList<Block> getBlocks(Block start, int radius){
        ArrayList<Block> blocks = new ArrayList<>();
        for(double x = start.getLocation().getX() - radius; x <= start.getLocation().getX() + radius; x++){
            for(double y = start.getLocation().getY() - radius; y <= start.getLocation().getY() + radius; y++){
                for(double z = start.getLocation().getZ() - radius; z <= start.getLocation().getZ() + radius; z++){
                    Location loc = new Location(start.getWorld(), x, y, z);
                    blocks.add(loc.getBlock());
                }
            }
        }
        return blocks;
    }

}