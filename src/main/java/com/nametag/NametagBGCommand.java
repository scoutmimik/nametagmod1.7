package fiw.nametageditor;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.BlockPos;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class NametagBGCommand implements ICommand {
   public String func_71517_b() {
      return "nametag";
   }

   public String func_71518_a(ICommandSender sender) {
      return "/nametag";
   }

   public void func_71515_b(ICommandSender sender, String[] args) throws CommandException {
      MinecraftForge.EVENT_BUS.register(this);
   }

   @SubscribeEvent
   public void onClientTick(TickEvent.ClientTickEvent event) {
      MinecraftForge.EVENT_BUS.unregister(this);
      Minecraft.func_71410_x().func_147108_a(new GUIMain());
   }

   public boolean func_71519_b(ICommandSender sender) {
      return true;
   }

   public int compareTo(ICommand o) {
      return 0;
   }

   public boolean func_82358_a(String[] args, int index) {
      return false;
   }

   public List<String> func_71514_a() {
      return new ArrayList();
   }

   public List<String> func_180525_a(ICommandSender sender, String[] args, BlockPos pos) {
      return null;
   }
}
