package com.olyno.skemail;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import com.olyno.skemail.types.EmailService;
import com.olyno.skemail.util.PackageFilter;
import com.olyno.skemail.util.Registration;

import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;
import jakarta.activation.CommandMap;
import jakarta.activation.MailcapCommandMap;

public class SkEmail extends JavaPlugin {

    public static SkEmail instance;
    private static File dataFolder;
    private static InputStream serviceResource;
    public List<Registration> expressions = new ArrayList<>();
    SkriptAddon addon;

    public static SkEmail getInstance() {
        return instance;
    }

    public static void success(String success) {
        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "[SkEmail] " + success);
    }

    public static void error(String error) {
        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "[SkEmail] " + error);
    }

    public static void loadServices() {
        File servicesFile = new File(dataFolder, "services.yml");
        if (!servicesFile.exists()) {
            servicesFile.getParentFile().mkdirs();
            copy(serviceResource, servicesFile);
        }
        FileConfiguration servicesYaml = YamlConfiguration.loadConfiguration(servicesFile);
        Set<String> servicesList = servicesYaml.getKeys(false);
        for (String service : servicesList) {
            EmailService theService = new EmailService();

            if (servicesYaml.get(service + ".name") != null) {
                theService.setName((String) servicesYaml.get(service + ".name"));
            } else {
                SkEmail.error("Can't load the service \"" + service + "\": It doesn't contain any name.");
                continue;
            }

            if (servicesYaml.get(service + ".smtp_address") != null) {
                theService.setSmtp_address((String) servicesYaml.get(service + ".smtp_address"));
            } else {
                SkEmail.error("Can't load the service \"" + service + "\": It doesn't contain any smtp address.");
                continue;
            }

            if (servicesYaml.get(service + ".smtp_port") != null) {
                theService.setSmtp_port(servicesYaml.get(service + ".smtp_port"));
            } else {
                SkEmail.error("Can't load the service \"" + service + "\": It doesn't contain any smtp port.");
                continue;
            }

            if (servicesYaml.get(service + ".imap_address") != null) {
                theService.setImap_address((String) servicesYaml.get(service + ".imap_address"));
            } else {
                SkEmail.error("Can't load the service \"" + service + "\": It doesn't contain any imap address.");
                continue;
            }

            if (servicesYaml.get(service + ".imap_port") != null) {
                theService.setImap_port(servicesYaml.get(service + ".imap_port"));
            } else {
                SkEmail.error("Can't load the service \"" + service + "\": It doesn't contain any imap port.");
                continue;
            }

            EmailService.services.remove(((String) servicesYaml.get(service + ".name")).toLowerCase());
            EmailService.services.put(((String) servicesYaml.get(service + ".name")).toLowerCase(), theService);
            SkEmail.success("Service named \"" + servicesYaml.get(service + ".name") + "\" has been loaded!");
        }

        MailcapCommandMap mc = (MailcapCommandMap) CommandMap.getDefaultCommandMap();
        mc.addMailcap("text/html;; x-java-content-handler=com.sun.mail.handlers.text_html");
        mc.addMailcap("text/xml;; x-java-content-handler=com.sun.mail.handlers.text_xml");
        mc.addMailcap("text/plain;; x-java-content-handler=com.sun.mail.handlers.text_plain");
        mc.addMailcap("multipart/*;; x-java-content-handler=com.sun.mail.handlers.multipart_mixed");
        mc.addMailcap("message/rfc822;; x-java-content-handler=com.sun.mail.handlers.message_rfc822");
        CommandMap.setDefaultCommandMap(mc);
        
    }

    private static void copy(InputStream in, File file) {
        try {
            OutputStream out = new FileOutputStream(file);
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            out.close();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public SkriptAddon getAddonInstance() {
        return addon;
    }

    public void onEnable() {

        // Register Addon
        instance = this;
        addon = Skript.registerAddon(this);
        try {
            addon.loadClasses("com.olyno.skemail");
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
        try {
			List<Class<Listener>> classes = new PackageFilter<Listener>("com.olyno.skemail.skript.events.bukkit").getClasses();
			for (Class<Listener> event : classes) {
				Listener evt = event.getDeclaredConstructor().newInstance();
				getServer().getPluginManager().registerEvents(evt, this);
			}
		} catch (IOException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}

        // Register Commands
        getCommand("skemail").setExecutor(new Commands(this));

        // Register Data
        dataFolder = getDataFolder();
        serviceResource = getResource("services.yml");

        // Load Services
        loadServices();

    }

}