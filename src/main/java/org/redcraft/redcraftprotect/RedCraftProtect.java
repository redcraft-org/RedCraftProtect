package org.redcraft.redcraftprotect;

import org.bukkit.Material;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.JavaPluginLoader;
import org.bukkit.scheduler.BukkitScheduler;
import org.redcraft.redcraftprotect.database.DatabaseManager;
import org.redcraft.redcraftprotect.listeners.blockListeners.BlockBreakListener;
import org.redcraft.redcraftprotect.listeners.blockListeners.BlockDispenseListener;
import org.redcraft.redcraftprotect.listeners.blockListeners.BlockPlaceListener;
import org.redcraft.redcraftprotect.listeners.entityListeners.EntityChangeBlockListener;
import org.redcraft.redcraftprotect.listeners.entityListeners.EntityExplodeListener;
import org.redcraft.redcraftprotect.listeners.inventoryListeners.InventoryClickListener;
import org.redcraft.redcraftprotect.listeners.inventoryListeners.InventoryMoveItemListener;
import org.redcraft.redcraftprotect.listeners.inventoryListeners.InventoryOpenListener;
import org.redcraft.redcraftprotect.listeners.inventoryListeners.InventoryPickupItemListener;
import org.redcraft.redcraftprotect.listeners.playerListeners.PlayerInteractListener;
import org.redcraft.redcraftprotect.models.world.ProtectedElements;
import org.redcraft.redcraftprotect.runnables.ContainerOwnersSynchronizerTask;
import org.redcraft.redcraftprotect.runnables.commands.CommandList;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class RedCraftProtect extends JavaPlugin {

    private static RedCraftProtect instance;
    public List<Material> protectedBlocks = Arrays.asList(
            Material.CHEST,
            Material.TRAPPED_CHEST,
            Material.FURNACE,
            Material.BLAST_FURNACE,
            Material.SMOKER,
            Material.BARREL,
            Material.HOPPER,
            Material.BEACON,
            Material.DISPENSER,
            Material.DROPPER,
            Material.LECTERN,
            Material.JUKEBOX,
            Material.CRAFTING_TABLE,
            Material.ANVIL
    );
    public RedCraftProtectUsers redCraftProtectUsers = new RedCraftProtectUsers();
    public ProtectedElements protectedElements = new ProtectedElements();
    private ContainerOwnersSynchronizerTask containerOwnersSynchronizerTask = new ContainerOwnersSynchronizerTask();
    private BlockPlaceListener blockPlaceListener = new BlockPlaceListener();
    private BlockBreakListener blockBreakListener = new BlockBreakListener();
    private EntityChangeBlockListener entityChangeBlockListener = new EntityChangeBlockListener();
    private EntityExplodeListener entityExplodeListener = new EntityExplodeListener();
    private InventoryMoveItemListener inventoryMoveItemListener = new InventoryMoveItemListener();
    private PlayerInteractListener playerInteractListener = new PlayerInteractListener();
    private InventoryClickListener inventoryClickListener = new InventoryClickListener();
    private InventoryOpenListener inventoryOpenListener = new InventoryOpenListener();
    private InventoryPickupItemListener onInventoryPickupItemListener = new InventoryPickupItemListener();
    private BlockDispenseListener onBlockDispenseListener = new BlockDispenseListener();

    public RedCraftProtect() {
        super();
    }

    protected RedCraftProtect(JavaPluginLoader loader, PluginDescriptionFile description, File dataFolder, File file) {
        super(loader, description, dataFolder, file);
    }

    static public RedCraftProtect getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;

        // Setup
        Config.readConfig(this);
        DatabaseManager.connect();

        // Schedulers
        BukkitScheduler scheduler = getServer().getScheduler();
        scheduler.runTaskTimerAsynchronously(this, containerOwnersSynchronizerTask, 5, 60);

        // Game listeners
        PluginManager pluginManager = this.getServer().getPluginManager();
        pluginManager.registerEvents(blockPlaceListener, this);
        pluginManager.registerEvents(blockBreakListener, this);
        pluginManager.registerEvents(entityExplodeListener, this);
        pluginManager.registerEvents(entityChangeBlockListener, this);
        pluginManager.registerEvents(inventoryMoveItemListener, this);
        pluginManager.registerEvents(playerInteractListener, this);
        pluginManager.registerEvents(inventoryClickListener, this);
        pluginManager.registerEvents(inventoryOpenListener, this);
        pluginManager.registerEvents(onInventoryPickupItemListener, this);
        pluginManager.registerEvents(onBlockDispenseListener, this);

        // Commands
        this.getCommand("rplist").setExecutor(new CommandList());
    }

    @Override
    public void onDisable() {
        getServer().getScheduler().cancelTasks(this);
    }
}