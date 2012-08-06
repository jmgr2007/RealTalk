package jmgr2007.bukkit.RealTalk;


import java.util.Set;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
/**
 * Events class
 * @author jmgr2007
 */
public class Events implements Listener {
	public static RealTalk plugin;
	public Configuration config;
	public Events(RealTalk instance) 
	{
		plugin = instance; 
	}
	@EventHandler
	/**
	 * When the player chats
	 */
	public void onPlayerChat(PlayerChatEvent event){
				String Message = event.getMessage();
				String prefix = "";
				String suffix = "";
				if(event.getPlayer().hasPermission("real.talk.color")) {
					Message = Message.replaceAll("&(?=[0-9a-fA-FkKmMoOlLnNrR])", "\u00a7");
				}
				if(plugin.getConfig().getBoolean("options.noise") == true) {
					Set<Player> players = event.getRecipients();
					for (Player player : players) {
							Location playerloc = player.getLocation();
							playerloc.getWorld().playEffect(playerloc, Effect.CLICK1, 0);
					}
				}
				if(event.getPlayer().isOp()) {
					prefix = plugin.getConfig().getString("chat.op.prefix");
					suffix = plugin.getConfig().getString("chat.op.suffix");
				}else {
					prefix = plugin.getConfig().getString("chat.default.prefix");
					suffix = plugin.getConfig().getString("chat.default.suffix");
				}
				for (int i = 1; i>=plugin.getConfig().getInt("options.customgroups"); i++) {
					if(event.getPlayer().hasPermission("real.talk.g" + i)) {
						prefix = plugin.getConfig().getString("chat.g" + i + ".prefix");
						suffix = plugin.getConfig().getString("chat.g" + i + ".suffix");
					}
				}
				//Player player = event.getPlayer();
				//prefix = IPermissionsHandler.getPrefix();
				prefix = prefix.replaceAll("&(?=[0-9a-fA-FkKmMoOlLnNrR])", "\u00a7");
				suffix = suffix.replaceAll("&(?=[0-9a-fA-FkKmMoOlLnNrR])", "\u00a7");
				// Added custom Formating
				String format = plugin.getConfig().getString("options.format");
				format = format.replace("{PREFIX}", prefix);
				format = format.replace("{SUFFIX}", suffix);
				format = format.replace("{NAME}", event.getPlayer().getDisplayName());
				format = format.replace("{MESSAGE}", Message);
				event.setFormat(format);
			}
	@EventHandler
	/**
	 * When player joins
	 */
	public void onPlayerJoin(PlayerJoinEvent event) {
		String Message = "";
		Player player = event.getPlayer();
		Message = plugin.getConfig().getString("messages.login");
		Message = Message.replaceAll("&(?=[0-9a-fA-FkKmMoOlLnNrR])", "\u00a7"); 
		Message = Message.replaceAll("%username%", player.getDisplayName());
		event.setJoinMessage(Message);
	}
	@EventHandler
	/**
	 * When player quits
	 */
	public void onPlayerQuit(PlayerQuitEvent event) {
		String Message = "";
		Player player = event.getPlayer();
		Message = plugin.getConfig().getString("messages.logout");
		Message = Message.replaceAll("&(?=[0-9a-fA-FkKmMoOlLnNrR])", "\u00a7"); 
		Message = Message.replaceAll("%username%", player.getDisplayName());
		event.setQuitMessage(Message);
		
	}
}