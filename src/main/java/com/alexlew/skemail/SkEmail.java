package com.alexlew.skemail;

import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;
import com.alexlew.skemail.events.OnConnection;
import com.alexlew.skemail.events.OnDisconnection;
import com.alexlew.skemail.events.OnTransport;
import com.alexlew.skemail.util.Registration;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SkEmail extends JavaPlugin {

	  static SkEmail instance;
	  SkriptAddon addon;
	  public List<Registration> expressions = new ArrayList<>();
	 
	   public void onEnable() {
	       instance = this;
	       addon = Skript.registerAddon(this);
	       try {
	           addon.loadClasses("com.alexlew.skemail");
	       } catch (IOException e) {
	           e.printStackTrace();
	       }
		
		   // Register Metrics
		   Metrics metrics = new Metrics(this);
		   metrics.addCustomChart(new Metrics.SimplePie("used_language", () ->
				   getConfig().getString("language", "en")));
		   metrics.addCustomChart(new Metrics.SimplePie("skript_version", () ->
				   Bukkit.getServer().getPluginManager().getPlugin("Skript").getDescription().getVersion()));
		   metrics.addCustomChart(new Metrics.SimplePie("skemail_version", () ->
				   this.getDescription().getVersion()));
		   metrics.addCustomChart(new Metrics.DrilldownPie("java_version", () -> {
			   Map<String, Map<String, Integer>> map = new HashMap<>();
			   String javaVersion = System.getProperty("java.version");
			   Map<String, Integer> entry = new HashMap<>();
			   entry.put(javaVersion, 1);
			   if (javaVersion.startsWith("1.7")) {
				   map.put("Java 1.7", entry);
			   } else if (javaVersion.startsWith("1.8")) {
				   map.put("Java 1.8", entry);
			   } else if (javaVersion.startsWith("1.9")) {
				   map.put("Java 1.9", entry);
			   } else {
				   map.put("Other", entry);
			   }
			   return map;
		   }));
		   
	       // Register events
		   new OnConnection(this);
		   new OnDisconnection(this);
		   new OnTransport(this);
		   
       }

       public static void error(String error) {
		   Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "[SkEmail] " + error);
	   }

	   public static SkEmail getInstance() {
	       return instance;
	   }
	 
	   public SkriptAddon getAddonInstance() {
	       return addon;
	   }
}