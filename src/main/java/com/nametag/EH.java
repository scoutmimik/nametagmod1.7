package fiw.nametageditor;

import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EH {
   @SubscribeEvent
   public void onLabelRender(RenderLivingEvent.Specials.Pre<EntityLivingBase> e) {
      e.setCanceled(true);
      NametagRenderer.a = e.renderer;
      NametagRenderer.renderName(e.entity, e.x, e.y, e.z);
   }
}
