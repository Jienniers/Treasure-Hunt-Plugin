package stephen.treasurehuntplugin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
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

        if (block != null) {
            BlockState blockState = block.getState();

            if (blockState instanceof Chest) {
                Chest chest = (Chest) blockState;

                if (plugin.treasures.containsKey(chest)) {
                    player.sendMessage(ChatColor.GREEN+"You found treasure!");

                    FireworkCeleberation(player);
                }
            }
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
