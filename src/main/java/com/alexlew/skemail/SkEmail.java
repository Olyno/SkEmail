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
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

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

           File f = new File("plugins/SkEmail/config.json");
           if(!f.exists()) {
               File d = new File("plugins/SkEmail");
               if(!d.exists()) {
                   new File(String.valueOf(d)).mkdirs();
               }
               new File(String.valueOf(f));
               JSONObject obj = new JSONObject();
               obj.put("login", "replace this with your email");
               obj.put("password", "replace this with the password of your email");
               obj.put("smtp_address", "smtp.gmail.com");
               obj.put("smtp_port", "465");
               obj.put("imap_address", "imap.gmail.com");
               obj.put("imap_port", "993");

               String objToString = obj.toJSONString().replace("{", "{\n\t");
               objToString = objToString.replace(",", ",\n\t");
               objToString = objToString.replace("\"}", "\"\n}");

               try (FileWriter file = new FileWriter(f)) {
                   file.write(objToString);
                   System.out.println("[SkEmail] Configuration's file has been generated with success!");
               } catch (IOException e) {
                   e.printStackTrace();
               }

           }

       }

	   public static SkEmail getInstance() {
	       return instance;
	   }
	 
	   public SkriptAddon getAddonInstance() {
	       return addon;
	   }
}