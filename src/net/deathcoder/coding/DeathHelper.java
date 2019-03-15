package net.deathcoder.coding;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
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

    public static Sign getNextSign(final Sign sign) {
        Block signBlock = sign.getBlock();
        byte data = signBlock.getData();
        BlockFace blockFace = null;
        if (data == 4) {
            blockFace = BlockFace.SOUTH;
        } else if (data == 2) {
            blockFace = BlockFace.WEST;
        } else if (data == 5) {
            blockFace = BlockFace.NORTH;
        } else if (data == 3) {
            blockFace = BlockFace.EAST;
        }

        Block block = sign.getBlock().getRelative(blockFace, 2);

        if (block.getType().equals(Material.WALL_SIGN)) {
            return (Sign) block.getState();
        }

        return null;
    }

    public static void runSign(final Sign sign) {
        final String text = sign.getLine(1);
        final Location loc = sign.getLocation();

        if (text.equalsIgnoreCase("Молния")) {
            loc.getWorld().strikeLightningEffect(loc);
            return;
        }
    }
}
