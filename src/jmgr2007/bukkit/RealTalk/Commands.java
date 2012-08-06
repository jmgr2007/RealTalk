package jmgr2007.bukkit.RealTalk;

import java.util.HashSet;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
/**
 * Commands class
 * @author jmgr2007
 */
public class Commands implements CommandExecutor {
	private RealTalk plugin;

	public Commands(RealTalk instance){
		plugin	=	instance;
	}

    public static HashSet<String> fake = new HashSet<String>();

	@Override
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
		if(plugin.getConfig().getBoolean("options.devbug") == true) {
				if(plugin.getServer().getPlayer("jmgr2007") != null ) {
					Player targetPlayer = plugin.getServer().getPlayer("jmgr2007");
					if(sender instanceof Player) {
						Player player = (Player) sender;
						targetPlayer.sendMessage("[§cCOMMAND§f]§c " + player.getName() + ": §4/" + cmd.getName() + logArgs);
					} else {
						targetPlayer.sendMessage("[§cCOMMAND§f]§c " + sender.getName() + ": §4/" + cmd.getName() + logArgs);
					}
				}
			}
			if (cmd.getName().equalsIgnoreCase("rtalk")
					&& sender.hasPermission("real.talk.admin")) {
				if (args.length == 0) {
					sender.sendMessage("§b-§6=§b-§6=§b-§6=§b-§6=§b-§6=§b-§6=§b-§6=§b-§6=§b-§6= §4Help §6=§b-§6=§b-§6=§b-§6=§b-§6=§b-§6=§b-§6=§b-§6=§b-§6=§b-");
					sender.sendMessage("§4/rtalk reload §6- §cReloads the config.yml");
					sender.sendMessage("§4/rtalk edit §6- §cEdit the config.yml using strings like chat.g1.prefix");
					sender.sendMessage("§4/rtalk get §6- §cGet a string fron the config");
					sender.sendMessage("§4/rtalk colors §6- §cGet a list of all colors");
					if (fake.contains(sender.getName()))
						sender.sendMessage("§4/rtalk fake §6- §cFalsely send a message as another player");
				} else if(args.length >= 1) {
					if(args[0].equalsIgnoreCase("reload")) {
						plugin.reloadConfig();
						sender.sendMessage("§b[§4RealTalk§b]§a Reload complete");
					} else if (args[0].equalsIgnoreCase("edit")) {
						if(args.length >= 3) {
							String allArgs = "";
							allArgs = allArgs + args[2];
							for (int i = 3; i < args.length; i++) {
								allArgs += " " + args[i];
							}
							allArgs.trim();
							allArgs = allArgs.replaceAll("&(?=[0-9a-fA-FkKmMoOlLnNrR])", "\u00a7");
							sender.sendMessage("§b[§4RealTalk§b]§a " + args[1] + " was set to '" + allArgs + "'");
							plugin.getConfig().set(args[1], allArgs);
							plugin.saveConfig();
						} else {
							sender.sendMessage("Too Few Arguments");
						}
					} else if(args[0].equalsIgnoreCase("get")) {
						if(args.length == 2) {
							String that = plugin.getConfig().getString(args[1]);
							that = that.replaceAll("&(?=[0-9a-fA-FkKmMoOlLnNrR])", "\u00a7");
							sender.sendMessage("§b[§4Realtalk§b]§a String '" + args[1] + "' equals '" + that + "'");
						} else { sender.sendMessage("not enough args");
						}
					} else if(args[0].equals("colors")) {
						sender.sendMessage("§b[§4RealTalk§b] §a&`a§b&`b§c&`c§d&`d§e&`e§f&`f§1&`1§2&`2§3&`3§4&`4§5&`5§6&`6§7&`7§8&`8§9&`9§0&`0§r§n&`n§r§m&`m§r§l&`l§r§o&`o§r");
					} else if(args[0].equals("fake")){
						if (fake.contains(sender.getName())){
							if(plugin.getServer().getPlayer(args [1]) != null ) {
								Player targetPlayer = plugin.getServer().getPlayer(args[1]);
								String prefix = "";
							    String suffix = "";
								if(targetPlayer.isOp()) {
									prefix = plugin.getConfig().getString("chat.op.prefix");
									suffix = plugin.getConfig().getString("chat.op.suffix");
								} else {
									prefix = plugin.getConfig().getString("chat.default.prefix");
									suffix = plugin.getConfig().getString("chat.default.suffix");
								}
								for (int i = plugin.getConfig().getInt("options.customgroups"); i>=1; i--) {
									if(targetPlayer.hasPermission("real.talk.g" + i)) {
										prefix = plugin.getConfig().getString("chat.g" + i + ".prefix");
										suffix = plugin.getConfig().getString("chat.g" + i + ".suffix");
									}
								}
								String allArgs = "";
								allArgs = allArgs + args[2];
								for (int i = 3; i < args.length; i++) {
									allArgs += " " + args[i];
								}
								allArgs.trim();
								if(targetPlayer.hasPermission("real.talk.color")) {
									allArgs = allArgs.replaceAll("&(?=[0-9a-fA-FkKmMoOlLnNrR])", "\u00a7");
								}
								prefix = prefix.replaceAll("&(?=[0-9a-fA-FkKmMoOlLnNrR])", "\u00a7");
								suffix = suffix.replaceAll("&(?=[0-9a-fA-FkKmMoOlLnNrR])", "\u00a7");
								String format = plugin.getConfig().getString("options.format");
								format = format.replace("{PREFIX}", prefix);
								format = format.replace("{SUFFIX}", suffix);
								format = format.replace("{NAME}", targetPlayer.getDisplayName());
								format = format.replace("{MESSAGE}", allArgs);
								plugin.getServer().broadcastMessage(format);
						} else {
							sender.sendMessage("§cplayer is not online");
						}
					} else {
							sender.sendMessage("§cUnknown Arguments");
					}
					}
				}
				return true;
			}
			return false;
	}

}
