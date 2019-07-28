package com.olyno;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import static org.bukkit.ChatColor.*;

public class Commands implements CommandExecutor {

    private final SkEmail plugin;

    public Commands(SkEmail plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) args = new String[]{"help"};
        if (command.getName().equalsIgnoreCase("skemail")) {
            if (args[0].equalsIgnoreCase("help")) {
                Bukkit.getServer().getConsoleSender().sendMessage(DARK_BLUE + "================= " + GOLD + "SkEmail" + DARK_BLUE + " =================");
                Bukkit.getServer().getConsoleSender().sendMessage("");
                Bukkit.getServer().getConsoleSender().sendMessage(BLUE + "/skemail reload" + GRAY + " - " + WHITE + "Reload the configuration of SkEmail (include services).");
            } else if (args[0].equalsIgnoreCase("reload")) {
                SkEmail.loadServices();
                SkEmail.success("SkEmail reloaded with success!");
            } else {
                Bukkit.getServer().getConsoleSender().sendMessage("This command doesn't exist. Try \"/skemail help\".");
            }
            return true;
        }
        return false;
    }
}
