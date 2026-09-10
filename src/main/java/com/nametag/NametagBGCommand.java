package com.nametag;

import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;

public class NametagBGCommand extends CommandBase {

   @Override
   public String getCommandName() {
      return "nametag";
   }

   @Override
   public String getCommandUsage(ICommandSender sender) {
      return "/nametag";
   }

   @Override
   public int getRequiredPermissionLevel() {
      return 0; // Umožní použitie príkazu aj bez OP tebe/hráčovi na serveroch
   }

   @Override
   public void processCommand(ICommandSender sender, String[] args) {
      // Registrácia na správnu FML zbernicu pre TickEvent
      FMLCommonHandler.instance().bus().register(this);
   }

   @SubscribeEvent
   public void onClientTick(TickEvent.ClientTickEvent event) {
      FMLCommonHandler.instance().bus().unregister(this);
      Minecraft.getMinecraft().displayGuiScreen(new GUIMain());
   }
}
