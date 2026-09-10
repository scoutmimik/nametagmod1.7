package com.nametag;

import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.client.event.RenderLivingEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class EH {
   @SubscribeEvent
   public void onLabelRender(RenderLivingEvent.Specials.Pre e) {
      e.setCanceled(true);
      NametagRenderer.a = e.renderer;
      NametagRenderer.renderName(e.entity, e.x, e.y, e.z);
   }
}
