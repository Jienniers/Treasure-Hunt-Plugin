package stephen.treasurehuntplugin;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

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
                new BukkitRunnable(){
                    int countdown = 5;

                    @Override
                    public void run(){
                        if (countdown == 0){
                            Location treasureLocation = generateTreasureLocation(player.getLocation());

                            Block block = treasureLocation.getBlock();
                            block.setType(Material.CHEST);

                            Chest treasureChest = (Chest) block.getState();

                            Inventory inventory = treasureChest.getInventory();
                            ItemStack[] rewards = generateRandomRewards();

                            inventory.addItem(rewards);

                            plugin.getLogger().info("Here is the Treasure Location: "+treasureChest.getLocation());

                            plugin.treasures.put(treasureChest, rewards);

                            player.sendMessage("A treasure hunt has begun! Find the hidden treasure chest.");
                            player.sendMessage(ChatColor.GREEN +"HINT: Treasure chest is at your 50 block radius from your current location.");
                            cancel();
                        }else{
                            player.sendMessage("Treasure hunt is starting in "+countdown);
                            countdown--;
                        }
                    }
                }.runTaskTimer(plugin, 0, 20);

            } else {
                sender.sendMessage("Only Admins can start the ");
            }
        } else {
            sender.sendMessage("Only players can start treasure hunts.");
        }
        return true;
    }


    private Location generateTreasureLocation(Location playerLocation) {
        Random random = new Random();
        int radius = 50;

        while (true) {
            int x = playerLocation.getBlockX() + random.nextInt(radius * 2) - radius;
            int z = playerLocation.getBlockZ() + random.nextInt(radius * 2) - radius;
            int y = playerLocation.getWorld().getHighestBlockYAt(x, z);

            Location location = new Location(playerLocation.getWorld(), x, y, z);

            Block blockBelow = location.clone().subtract(0, 1, 0).getBlock();

            if (blockBelow.getType().isSolid()) {
                return location;
            }
        }
    }


    private ItemStack[] generateRandomRewards() {
        Random random = new Random();

        Material[] commonItems = {
                Material.DIAMOND_SWORD,
                Material.DIAMOND,
                Material.GOLD_INGOT,
                Material.EMERALD,
                Material.ENCHANTED_GOLDEN_APPLE,
                Material.COOKED_BEEF,
                Material.COOKED_PORKCHOP,
                Material.GOLDEN_CARROT,
                Material.ENCHANTED_BOOK,
                Material.ENDER_PEARL,
                Material.OBSIDIAN,
                Material.LAPIS_LAZULI,
                Material.QUARTZ,
                Material.REDSTONE,
                Material.GLOWSTONE_DUST,
                Material.SLIME_BALL
        };

        Material[] rareItems = {
                Material.NETHER_STAR,
                Material.ELYTRA,
                Material.DRAGON_EGG,
                Material.ENCHANTED_GOLDEN_APPLE,
                Material.TOTEM_OF_UNDYING,
                Material.TRIDENT,
                Material.SHULKER_BOX,
                Material.BEACON
        };

        int numberOfRewards = 3;
        ItemStack[] rewards = new ItemStack[numberOfRewards];

        for (int i = 0; i < numberOfRewards; i++) {
            ItemStack item;

            if (random.nextInt(100) < 80) {
                item = new ItemStack(commonItems[random.nextInt(commonItems.length)], random.nextInt(3) + 1);
            } else {
                item = new ItemStack(rareItems[random.nextInt(rareItems.length)], 1);
            }

            rewards[i] = item;
        }

        return rewards;
    }
}
