package stephen.treasurehuntplugin;

import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public class TreasureHuntPlugin extends JavaPlugin {
    public Map<Location, ItemStack[]> treasureLocations = new HashMap<>();

    @Override
    public void onEnable() {
        new Listeners(this);
        new Command(this);
    }
}
