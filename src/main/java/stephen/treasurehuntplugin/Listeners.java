package stephen.treasurehuntplugin;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class Listeners implements Listener {
    private TreasureHuntPlugin plugin;

    public Listeners(TreasureHuntPlugin plugin){
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Block block = event.getClickedBlock();
        if (block != null && plugin.treasureLocations.containsKey(block.getLocation())) {
            ItemStack[] rewards = plugin.treasureLocations.get(block.getLocation());
            for (ItemStack item : rewards) {
                player.getInventory().addItem(item);
            }
            player.sendMessage("You found treasure!");
        }
    }

}
