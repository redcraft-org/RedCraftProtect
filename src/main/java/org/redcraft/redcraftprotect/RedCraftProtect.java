package org.redcraft.redcraftprotect;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.redcraft.redcraftprotect.database.DatabaseManager;
import org.redcraft.redcraftprotect.listeners.BlockPlaceListener;
import org.redcraft.redcraftprotect.runnables.ContainerOwnersSynchronizerTask;

public class RedCraftProtect extends JavaPlugin {

	private static RedCraftProtect instance;

	private ContainerOwnersSynchronizerTask containerOwnersSynchronizerTask = new ContainerOwnersSynchronizerTask();

	private BlockPlaceListener minecraftChatListener = new BlockPlaceListener();

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
		pluginManager.registerEvents(minecraftChatListener, this);
	}

	@Override
	public void onDisable() {
		getServer().getScheduler().cancelTasks(this);
	}

	static public RedCraftProtect getInstance() {
		return instance;
	}
}