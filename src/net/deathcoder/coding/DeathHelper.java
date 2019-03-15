package net.deathcoder.coding;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.*;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

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

    public static void runSign(Sign sign, Map<String, Object> globalVars) {
        if (sign.getLine(0).equalsIgnoreCase("[event]")) {
            return;
        }

        String text = sign.getLine(1);
        Location signLoc = sign.getLocation();
        List<Object> localVars = getVarsFromSign(sign);

        if (text.equalsIgnoreCase("Молния")) {
            for (Object var : localVars) {
                if (var instanceof Location) {
                    Location loc = (Location) var;
                    loc.getWorld().strikeLightning(loc);
                }
            }
        }

        if (text.equalsIgnoreCase("Ломать дважды")) {
            Block block = (Block) globalVars.get("block-break");
            block.getRelative(BlockFace.DOWN).setType(Material.AIR);
        }
    }

    public static List<Object> getVarsFromSign(Sign sign) {
        Block signBlock = sign.getBlock();
        byte data = signBlock.getData();

        BlockFace blockFace = null;
        if (data == 3) {
            blockFace = BlockFace.NORTH;
        } else if (data == 4) {
            blockFace = BlockFace.EAST;
        } else if (data == 2) {
            blockFace = BlockFace.SOUTH;
        } else if (data == 5) {
            blockFace = BlockFace.WEST;
        }

        Block wallBlock = signBlock.getRelative(blockFace);
        Block chectBlock = wallBlock.getRelative(BlockFace.UP);

        if (!chectBlock.getType().equals(Material.CHEST)) {
            return new ArrayList<>();
        }

        Chest chest = (Chest) chectBlock.getState();

        List<Object> vars = new ArrayList<>();
        for (ItemStack item : chest.getBlockInventory().getContents()) {
            if (item != null) {
                ItemMeta itemMeta = item.getItemMeta();
                String textData = itemMeta.getLocalizedName();

                if (textData.startsWith("var-location")) {
                    String[] split = textData.split(":");
                    String worldName = split[1];
                    Integer x = Integer.parseInt(split[2]);
                    Integer y = Integer.parseInt(split[3]);
                    Integer z = Integer.parseInt(split[4]);
                    World world = Bukkit.getWorld(worldName);

                    Location location = new Location(world, x, y, z);
                    vars.add(location);
                }
            }
        }

        return vars;
    }
}
