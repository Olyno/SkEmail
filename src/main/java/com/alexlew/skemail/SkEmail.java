package com.alexlew.skemail;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.plugin.java.JavaPlugin;

import com.alexlew.skemail.util.Registration;

import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;

public class SkEmail extends JavaPlugin {

	  static SkEmail instance;
	  SkriptAddon addon;
	  public List<Registration> expressions = new ArrayList<>();
	 
	   public void onEnable() {
	       instance = this;
	       addon = Skript.registerAddon(this);
	       try {
	           addon.loadClasses("com.alexlew.skemail", "effects", "expressions", "scopes", "types");
	       } catch (IOException e) {
	           e.printStackTrace();
	       }

       }

       public static void error(String error) {
	   		System.out.println("\u001B[31m[SkEmail] " + error + "\u001B[0m");
	   }

	   public static SkEmail getInstance() {
	       return instance;
	   }
	 
	   public SkriptAddon getAddonInstance() {
	       return addon;
	   }
}