package com.AlexLew.SkEmail;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.AlexLew.SkEmail.Skript.Utils.Registration;

import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;

public class SkEmail_main extends JavaPlugin {

	  static SkEmail_main instance;
	  SkriptAddon addon;
	  public List<Registration> expressions = new ArrayList<>();
	 
	   public void onEnable() {
	       instance = this;
	       addon = Skript.registerAddon(this);
	       try {
	           addon.loadClasses("com.AlexLew.SkEmail", "Skript");
	       } catch (IOException e) {
	           e.printStackTrace();
	       }
	       Bukkit.getLogger().info("[SkEmail] has been enabled!");
	   }
	 
	   public static SkEmail_main getInstance() {
	       return instance;
	   }
	 
	   public SkriptAddon getAddonInstance() {
	       return addon;
	   }
}