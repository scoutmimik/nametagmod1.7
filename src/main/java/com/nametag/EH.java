package com.nametag;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderLivingEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class EH {
   @SubscribeEvent
   public void onLabelRender(RenderLivingEvent.Specials.Pre e) {
      // Reagujeme IBA na reálnych hráčov
      if (e.entity instanceof EntityPlayer) {
         // Zrušíme iba originálny render hráča
         e.setCanceled(true);
         
         NametagRenderer.a = e.renderer;
         NametagRenderer.renderName(e.entity, e.x, e.y, e.z);
      }
      // Pre všetky ostatné entity (NPC, hologramy, mobov) event NEZRUŠÍME.
      // Hra pre ne vykreslí ich vlastné nametagy/hologramy presne na mieste, kde majú byť.
   }
}
