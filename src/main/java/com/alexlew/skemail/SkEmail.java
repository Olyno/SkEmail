package com.alexlew.skemail;

import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;
import com.alexlew.skemail.events.OnConnection;
import com.alexlew.skemail.events.OnTransport;
import com.alexlew.skemail.util.Registration;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

	       // Register events
		   new OnTransport(this);
	       new OnConnection(this);
	       
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