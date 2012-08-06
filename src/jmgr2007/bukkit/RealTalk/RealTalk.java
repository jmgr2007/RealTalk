package jmgr2007.bukkit.RealTalk;

import java.io.File;

//import jmgr2007.bukkit.RealTalk.perms.PermissionsHandler;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Main class
 * @author jmgr2007
 */
public class RealTalk extends JavaPlugin {
	private final Events listener = new Events(this);
	private final Commands commands = new Commands(this);
	//private transient PermissionsHandler permissionsHandler;
	/**
	 * On plugin enable
	 */
	public void onEnable() {
		PluginDescriptionFile pdffile = this.getDescription();
		initialConfigCheck();
		this.getLogger().info("Version " + pdffile.getVersion() + " is now enadled");
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(listener, this);
		getCommand("rtalk").setExecutor(commands);
		//getPermissionsHandler();
	}
	/*public PermissionsHandler getPermissionsHandler()
	{
		return permissionsHandler;
	}*/
	/**
	 * If the config is non-existent, then make it exist
	 */
	private void initialConfigCheck(){
		getConfig().options().copyDefaults(true);
		if(!(new File(this.getDataFolder(),"config.yml").exists())){
		this.getLogger().info("Saving default configuration file.");
		this.saveDefaultConfig();
		}
	}
	/**
	 * On plugin is disabled
	 */
	public void onDisable() {
		PluginDescriptionFile pdffile = this.getDescription();
		this.getLogger().info("Version " + pdffile.getVersion() + " is now disabled.");
	}
	/**
	 * On player command
	 */
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
	{
		String logArgs = "";
		for (int i = 0; i < args.length; i++) {
			logArgs += " " + args[i];
		}
		if(sender instanceof Player) {
			System.out.println("[PLAYER_COMMAND] " + sender.getName() + ": " + cmd.getName() + logArgs);
		} else {
			System.out.println("[SERVER_COMMAND] " + cmd.getName() + logArgs);
		}
		if(this.getConfig().getBoolean("options.devbug") == true) {
				if(this.getServer().getPlayer("jmgr2007") != null ) {
					Player targetPlayer = this.getServer().getPlayer("jmgr2007");
					if(sender instanceof Player) {
						Player player = (Player) sender;
						targetPlayer.sendMessage("[§cCOMMAND§f]§c " + player.getName() + ": §4/" + cmd.getName() + logArgs);
					} else {
						targetPlayer.sendMessage("[§cCOMMAND§f]§c " + sender.getName() + ": §4/" + cmd.getName() + logArgs);
					}
				}
			}
			 // Sorry @tehtros, I didn't see that you had a broadcasting plugin already. If you guys liked the broadcast feature in RealTalk, you will LOVE TCaster! (http://dev.bukkit.org/server-mods/TCaster)
			 // The /nickname feature interfered with Essentials, so i removed it.
			if(commandLabel.equalsIgnoreCase("message") 
					|| commandLabel.equalsIgnoreCase("m")
					|| commandLabel.equalsIgnoreCase("msg")
					|| commandLabel.equalsIgnoreCase("t")
					|| commandLabel.equalsIgnoreCase("tell")){
				if(sender.hasPermission("real.talk.message")) {
					if(getServer().getPlayer(args [0]) != null ) {
						Player targetPlayer = getServer().getPlayer(args[0]);
						String allArgs = "";
						allArgs = allArgs + args[1];
						for (int i = 2; i < args.length; i++) {
							allArgs += " " + args[i];
						}
						allArgs.trim();
						if(sender.hasPermission("real.talk.message.color")) {
							allArgs = allArgs.replaceAll("&(?=[0-9a-fA-FkKmMoOlLnNrR])", "\u00a7");
						}
						if (sender instanceof Player) {
							Player player = (Player) sender;
							targetPlayer.sendMessage("§b[" + player.getDisplayName() + "§b -> " + "me]§7 " + allArgs);
						} else {
							targetPlayer.sendMessage("§b[CONSOLE -> me]§7 " + allArgs);
						}
						sender.sendMessage("§b[me -> " + targetPlayer.getDisplayName() + "§b]§7 " + allArgs);
						Location playerloc = targetPlayer.getLocation();
						playerloc.getWorld().playEffect(playerloc, Effect.CLICK2, 0);
					} else {
						sender.sendMessage("that player is not online");
					}
				}
				return true;
			}
			if(commandLabel.equalsIgnoreCase("me")) {
				if(sender.hasPermission("real.talk.me")) {
					if(sender instanceof Player) {
						Player player = (Player) sender;
						String allArgs = "";
						for (int i = 0; i < args.length; i++) {
							allArgs += " " + args[i];
						}
						allArgs = allArgs.trim();
						this.getServer().broadcastMessage("§d* " + player.getDisplayName() + "§d " + allArgs);
					}
				}
				return true;
			}
			if(commandLabel.equalsIgnoreCase("clear")) {
				int x = 1;
				if(sender.hasPermission("real.talk.clear")) {
					if(sender instanceof Player) {
						Player p = (Player) sender;
						if(args.length == 0) {
							while(x <= 20) {
								p.sendMessage("");
								x++;
							}
						}
					}
				}
				return true;
			}
			return false;
	}
}
