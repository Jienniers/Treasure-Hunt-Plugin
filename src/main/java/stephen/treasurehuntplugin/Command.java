package stephen.treasurehuntplugin;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class Command implements CommandExecutor {

    private TreasureHuntPlugin plugin;

    public Command(TreasureHuntPlugin plugin){
        this.plugin = plugin;
        plugin.getCommand("startquest").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.isOp()) {
                Location treasureLocation = generateTreasureLocation(player.getLocation());
                if (treasureLocation != null) {
                    Block treasureChest = treasureLocation.getBlock();
                    treasureChest.setType(Material.CHEST);
                    ItemStack[] rewards = generateRandomRewards();
                    treasureChest.getWorld().dropItemNaturally(treasureChest.getLocation(), rewards[0]);
                    treasureChest.getWorld().dropItemNaturally(treasureChest.getLocation(), rewards[1]);
                    plugin.treasureLocations.put(treasureChest.getLocation(), rewards);
                    player.sendMessage("A treasure hunt has begun! Find the hidden treasure chest.");
                } else {
                    player.sendMessage("Unable to generate a treasure location. Try again later.");
                }
            } else {
                sender.sendMessage("Only players can start treasure hunts.");
            }
        }
        return true;
    }

    private Location generateTreasureLocation(Location playerLocation) {
        Random random = new Random();
        int radius = 50;
        int x = playerLocation.getBlockX() + random.nextInt(radius * 2) - radius;
        int z = playerLocation.getBlockZ() + random.nextInt(radius * 2) - radius;
        int y = playerLocation.getWorld().getHighestBlockYAt(x, z);
        return new Location(playerLocation.getWorld(), x, y, z);
    }

    private ItemStack[] generateRandomRewards() {
        ItemStack[] rewards = new ItemStack[2];
        rewards[0] = new ItemStack(Material.DIAMOND, 3);
        rewards[1] = new ItemStack(Material.GOLD_INGOT, 5);
        return rewards;
    }
}
