package stephen.treasurehuntplugin;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.scheduler.BukkitRunnable;

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

            FireworkCeleberation(player);
        }
    }

    private void FireworkCeleberation(Player player){
        final long duration = 5 * 20L;

        new BukkitRunnable() {
            long timeElapsed = 0;

            @Override
            public void run() {
                FireworkEffect effect = FireworkEffect.builder()
                        .withColor(Color.RED)
                        .withFade(Color.YELLOW)
                        .with(FireworkEffect.Type.BURST)
                        .withFlicker()
                        .withTrail()
                        .build();

                Firework firework = player.getWorld().spawn(player.getLocation(), Firework.class);

                FireworkMeta meta = firework.getFireworkMeta();
                meta.addEffect(effect);
                meta.setPower(1);
                firework.setFireworkMeta(meta);

                timeElapsed += 10;

                if (timeElapsed >= duration) {
                    cancel();
                }
            }
        }.runTaskTimer(plugin, 0L, 10L);
    }
}
