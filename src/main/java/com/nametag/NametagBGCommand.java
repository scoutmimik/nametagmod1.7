package com.nametag;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;

public class NametagBGCommand implements ICommand {
   public String getCommandName() {
      return "nametag";
   }

   public String getCommandUsage(ICommandSender sender) {
      return "/nametag";
   }

   public void processCommand(ICommandSender sender, String[] args) throws CommandException {
      MinecraftForge.EVENT_BUS.register(this);
   }

   @SubscribeEvent
   public void onClientTick(TickEvent.ClientTickEvent event) {
      MinecraftForge.EVENT_BUS.unregister(this);
      Minecraft.getMinecraft().displayGuiScreen(new GUIMain());
   }

   public boolean canCommandSenderUseCommand(ICommandSender sender) {
      return true;
   }

   public int compareTo(Object o) {
      return 0;
   }

   public boolean isUsernameIndex(String[] args, int index) {
      return false;
   }

   public List getCommandAliases() {
      return new ArrayList();
   }

   public List addTabCompletionOptions(ICommandSender sender, String[] args) {
      return null;
   }
}
