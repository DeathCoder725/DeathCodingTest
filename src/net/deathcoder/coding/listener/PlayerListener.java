package net.deathcoder.coding.listener;

import com.mojang.authlib.GameProfile;
import net.deathcoder.coding.DeathCodingPlugin;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

import static net.deathcoder.coding.DeathHelper.*;

public class PlayerListener implements Listener {
    private final DeathCodingPlugin plugin;
    private Set<Sign> onEvents = new HashSet<>();

    public PlayerListener(final DeathCodingPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInteract(final PlayerInteractEvent event) {
        if (event.getClickedBlock() != null && event.getClickedBlock().getType() ==  Material.WALL_SIGN) {
            Player player = event.getPlayer();
            Block block = event.getClickedBlock();
            Sign signBlock = (Sign) block.getState();
            this.runCode(player, signBlock, new HashMap<>());
        } else if (event.getHand().equals(EquipmentSlot.HAND)
                && event.getClickedBlock() != null
                && event.getClickedBlock().getType() != Material.CHEST
                && event.getItem() != null
                && event.getItem().getType() == Material.SLIME_BALL) {
            this.setLocationItem(event);
        }
    }


    @EventHandler
    public void on(BlockBreakEvent event) {
        for (Sign sign : onEvents) {
            if (sign.getLine(1).equalsIgnoreCase("ломать блок")) {

                HashMap<String, Object> vars = new HashMap<>();
                vars.put("block-break", event.getBlock());
                vars.put("player-break", event.getPlayer());

                runCode(sign, vars);
            }
        }
    }

    private void runCode(Player player, Sign sign, Map<String, Object> globalVars) {
        if (sign.getLine(0).equalsIgnoreCase("[event]")) {
            onEvents.add(sign);
            player.sendMessage("добавили табличку в event'ы");
        } else {
            runCode(sign, globalVars);
        }
    }

    private void runCode(Sign sign, Map<String, Object> globalVars) {
        do {
            runSign(sign, globalVars);
        } while ((sign = getNextSign(sign)) != null);
    }

    private void setLocationItem(PlayerInteractEvent event) {
        ItemStack item = event.getItem();
        Block clickedBlock = event.getClickedBlock();

        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setLocalizedName("var-location:" +
                clickedBlock.getWorld().getName() +
                ":" + clickedBlock.getX() +
                ":" + clickedBlock.getY() +
                ":" + clickedBlock.getZ());

        itemMeta.setDisplayName("Локация: " +
                clickedBlock.getWorld().getName() +
                " " + clickedBlock.getX() +
                "," + clickedBlock.getY() +
                "," + clickedBlock.getZ());
        item.setItemMeta(itemMeta);
        event.getPlayer().getInventory().setItemInMainHand(item);

        event.getPlayer().sendMessage("Локация установлена!");
    }
}
