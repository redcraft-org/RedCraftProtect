package org.redcraft.redcraftprotect;

import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.redcraft.redcraftprotect.database.DatabaseManager;
import org.redcraft.redcraftprotect.listeners.blockListeners.BlockBreakListener;
import org.redcraft.redcraftprotect.listeners.blockListeners.BlockPlaceListener;
import org.redcraft.redcraftprotect.listeners.entityListeners.EntityExplodeListener;
import org.redcraft.redcraftprotect.listeners.inventoryListeners.InventoryMoveItemListener;
import org.redcraft.redcraftprotect.listeners.playerListeners.PlayerInteractListener;
import org.redcraft.redcraftprotect.models.world.ProtectedElements;
import org.redcraft.redcraftprotect.runnables.ContainerOwnersSynchronizerTask;
import org.redcraft.redcraftprotect.runnables.commands.CommandList;

import java.util.Arrays;
import java.util.List;

public class RedCraftProtect extends JavaPlugin {

	private static RedCraftProtect instance;

	private ContainerOwnersSynchronizerTask containerOwnersSynchronizerTask = new ContainerOwnersSynchronizerTask();

	private BlockPlaceListener blockPlaceListener = new BlockPlaceListener();
	private BlockBreakListener blockBreakListener = new BlockBreakListener();
	private EntityExplodeListener entityExplodeListener = new EntityExplodeListener();
	private InventoryMoveItemListener inventoryMoveItemListener = new InventoryMoveItemListener();
	private PlayerInteractListener playerInteractListener = new PlayerInteractListener();


	public ProtectedElements protectedElements = new ProtectedElements();
	public List<String> protectedBlocks = Arrays.asList("CHEST", "WORKBENCH", "HOPPER");

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
		pluginManager.registerEvents(inventoryMoveItemListener, this);
		pluginManager.registerEvents(playerInteractListener, this);

		// Commands
		this.getCommand("rplist").setExecutor(new CommandList());
	}

	@Override
	public void onDisable() {
		getServer().getScheduler().cancelTasks(this);
	}

	static public RedCraftProtect getInstance() {
		return instance;
	}
}