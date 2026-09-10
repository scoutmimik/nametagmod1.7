package com.nametag;

import java.io.File;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(
   modid = "nametagmod",
   acceptedMinecraftVersions = "[1.7.10]"
)
public class Main {
   public static Configuration config;

   @EventHandler
   public void preInit(FMLPreInitializationEvent event) {
      MinecraftForge.EVENT_BUS.register(new EH());
   }

   @EventHandler
   public void init(FMLInitializationEvent event) {
      ClientCommandHandler.instance.registerCommand(new NametagBGCommand());
      System.out.println(Minecraft.getMinecraft().thePlayer);
      this.loadConfig();
   }

   public void loadConfig() {
      try {
         config = new Configuration(new File(Minecraft.getMinecraft().mcDataDir + "/config/NametagEditor.cfg"));
         config.load();
         int alpha = config.get("Background", "Alpha", 25).getInt();
         int offset = config.get("Player nametags", "Y offset", 0).getInt();
         int scale = config.get("Player nametags", "Scale", 100).getInt();
         boolean selftag = config.get("Other", "Own nametag", false).getBoolean();
         if (alpha < 0 || alpha > 25) {
            alpha = 25;
         }

         if (offset < -20 || offset > 0) {
            offset = 0;
         }

         if (scale < 0 || scale > 100) {
            scale = 100;
         }

         NametagRenderer.setAlpha(alpha);
         NametagRenderer.setOffset(offset);
         NametagRenderer.setScale(scale);
         NametagRenderer.setSelftag(selftag);
      } catch (Exception var8) {
         System.out.println("Error loading config, returning to default variables.");
      } finally {
         config.save();
      }
   }
}
