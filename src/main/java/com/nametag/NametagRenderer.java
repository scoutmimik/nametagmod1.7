package fiw.nametageditor;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.Team;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.common.MinecraftForge;
import org.lwjgl.opengl.GL11;

public class NametagRenderer {
   public static float alpha = 0.25F;
   public static float offset = 0.0F;
   public static float scale = 1.0F;
   public static boolean selftag = false;
   public static RendererLivingEntity a;

   public static void renderName(EntityLivingBase entity, double x, double y, double z) {
      boolean isPlayer = entity instanceof EntityPlayer;
      if (isPlayer) {
         if (scale == 0.0F) {
            return;
         }

         y += (double)offset;
      }

      if (entity instanceof EntityArmorStand && entity.func_174833_aM() || canRenderName(entity)) {
         double d0 = entity.func_70068_e(a.func_177068_d().field_78734_h);
         float f = entity.func_70093_af() ? RendererLivingEntity.NAME_TAG_RANGE_SNEAK : RendererLivingEntity.NAME_TAG_RANGE;
         if (d0 < (double)(f * f)) {
            String s = entity.func_145748_c_().func_150254_d();
            float f1 = isPlayer ? 0.02666667F * scale : 0.02666667F;
            GlStateManager.func_179092_a(516, 0.1F);
            if (entity.func_70093_af()) {
               FontRenderer fontrenderer = a.func_76983_a();
               GlStateManager.func_179094_E();
               GlStateManager.func_179109_b((float)x, (float)y + entity.field_70131_O + 0.5F - (entity.func_70631_g_() ? entity.field_70131_O / 2.0F : 0.0F), (float)z);
               GL11.glNormal3f(0.0F, 1.0F, 0.0F);
               GlStateManager.func_179114_b(-a.func_177068_d().field_78735_i, 0.0F, 1.0F, 0.0F);
               GlStateManager.func_179114_b(a.func_177068_d().field_78732_j, 1.0F, 0.0F, 0.0F);
               GlStateManager.func_179152_a(-f1, -f1, f1);
               GlStateManager.func_179109_b(0.0F, 9.374999F, 0.0F);
               GlStateManager.func_179140_f();
               GlStateManager.func_179132_a(false);
               GlStateManager.func_179147_l();
               GlStateManager.func_179090_x();
               GlStateManager.func_179120_a(770, 771, 1, 0);
               int i = fontrenderer.func_78256_a(s) / 2;
               Tessellator tessellator = Tessellator.func_178181_a();
               WorldRenderer worldrenderer = tessellator.func_178180_c();
               worldrenderer.func_181668_a(7, DefaultVertexFormats.field_181706_f);
               if (alpha != 0.0F) {
                  worldrenderer.func_181662_b((double)(-i - 1), (double)-1.0F, (double)0.0F).func_181666_a(0.0F, 0.0F, 0.0F, alpha).func_181675_d();
                  worldrenderer.func_181662_b((double)(-i - 1), (double)8.0F, (double)0.0F).func_181666_a(0.0F, 0.0F, 0.0F, alpha).func_181675_d();
                  worldrenderer.func_181662_b((double)(i + 1), (double)8.0F, (double)0.0F).func_181666_a(0.0F, 0.0F, 0.0F, alpha).func_181675_d();
                  worldrenderer.func_181662_b((double)(i + 1), (double)-1.0F, (double)0.0F).func_181666_a(0.0F, 0.0F, 0.0F, alpha).func_181675_d();
               }

               tessellator.func_78381_a();
               GlStateManager.func_179098_w();
               GlStateManager.func_179132_a(true);
               fontrenderer.func_78276_b(s, -fontrenderer.func_78256_a(s) / 2, 0, 553648127);
               GlStateManager.func_179145_e();
               GlStateManager.func_179084_k();
               GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
               GlStateManager.func_179121_F();
            } else if (isPlayer) {
               playerRenderOffsetLivingLabel(entity, x, y - (entity.func_70631_g_() ? (double)(entity.field_70131_O / 2.0F) : (double)0.0F), z, s, 0.02666667F, d0);
            } else {
               renderOffsetLivingLabel(entity, x, y - (entity.func_70631_g_() ? (double)(entity.field_70131_O / 2.0F) : (double)0.0F), z, s, 0.02666667F, d0, false);
            }
         }
      }

      MinecraftForge.EVENT_BUS.post(new RenderLivingEvent.Specials.Post(entity, a, x, y, z));
   }

   protected static void playerRenderOffsetLivingLabel(EntityLivingBase entityIn, double x, double y, double z, String str, float p_177069_9_, double p_177069_10_) {
      if (p_177069_10_ < (double)100.0F) {
         Scoreboard scoreboard = ((EntityPlayer)entityIn).func_96123_co();
         ScoreObjective scoreobjective = scoreboard.func_96539_a(2);
         if (scoreobjective != null) {
            Score score = scoreboard.func_96529_a(entityIn.func_70005_c_(), scoreobjective);
            renderLivingLabel(entityIn, score.func_96652_c() + " " + scoreobjective.func_96678_d(), x, y, z, 64, true);
            y += (double)((float)a.func_76983_a().field_78288_b * 1.15F * p_177069_9_);
         }
      }

      renderOffsetLivingLabel(entityIn, x, y, z, str, p_177069_9_, p_177069_10_, true);
   }

   protected static boolean canRenderName(EntityLivingBase entity) {
      return canRenderName2(entity) && (entity.func_94059_bO() || entity.func_145818_k_() && entity == a.func_177068_d().field_147941_i);
   }

   protected static boolean canRenderName2(EntityLivingBase entity) {
      if (entity == a.func_177068_d().field_78734_h) {
         return selftag;
      } else {
         EntityPlayerSP entityplayersp = Minecraft.func_71410_x().field_71439_g;
         if (entity instanceof EntityPlayer && entity != entityplayersp) {
            Team team = entity.func_96124_cp();
            Team team1 = entityplayersp.func_96124_cp();
            if (team != null) {
               Team.EnumVisible team$enumvisible = team.func_178770_i();
               switch (team$enumvisible) {
                  case ALWAYS:
                     return true;
                  case NEVER:
                     return false;
                  case HIDE_FOR_OTHER_TEAMS:
                     return team1 == null || team.func_142054_a(team1);
                  case HIDE_FOR_OWN_TEAM:
                     return team1 == null || !team.func_142054_a(team1);
                  default:
                     return true;
               }
            }
         }

         return Minecraft.func_71382_s() && !entity.func_98034_c(entityplayersp) && entity.field_70153_n == null;
      }
   }

   protected static void renderOffsetLivingLabel(EntityLivingBase entityIn, double x, double y, double z, String str, float p_177069_9_, double p_177069_10_, boolean isPlayer) {
      renderLivingLabel(entityIn, str, x, y, z, 64, isPlayer);
   }

   protected static void renderLivingLabel(EntityLivingBase entityIn, String str, double x, double y, double z, int maxDistance, boolean isPlayer) {
      double d0 = entityIn.func_70068_e(a.func_177068_d().field_78734_h);
      if (d0 <= (double)(maxDistance * maxDistance)) {
         FontRenderer fontrenderer = a.func_76983_a();
         float f1 = isPlayer ? 0.02666667F * scale : 0.02666667F;
         GlStateManager.func_179094_E();
         GlStateManager.func_179109_b((float)x + 0.0F, (float)y + entityIn.field_70131_O + 0.5F, (float)z);
         GL11.glNormal3f(0.0F, 1.0F, 0.0F);
         GlStateManager.func_179114_b(-a.func_177068_d().field_78735_i, 0.0F, 1.0F, 0.0F);
         GlStateManager.func_179114_b(a.func_177068_d().field_78732_j, 1.0F, 0.0F, 0.0F);
         GlStateManager.func_179152_a(-f1, -f1, f1);
         GlStateManager.func_179140_f();
         GlStateManager.func_179132_a(false);
         GlStateManager.func_179097_i();
         GlStateManager.func_179147_l();
         GlStateManager.func_179120_a(770, 771, 1, 0);
         Tessellator tessellator = Tessellator.func_178181_a();
         WorldRenderer worldrenderer = tessellator.func_178180_c();
         int i = 0;
         if (str.equals("deadmau5")) {
            i = -10;
         }

         int j = fontrenderer.func_78256_a(str) / 2;
         GlStateManager.func_179090_x();
         worldrenderer.func_181668_a(7, DefaultVertexFormats.field_181706_f);
         if (alpha != 0.0F) {
            worldrenderer.func_181662_b((double)(-j - 1), (double)(-1 + i), (double)0.0F).func_181666_a(0.0F, 0.0F, 0.0F, alpha).func_181675_d();
            worldrenderer.func_181662_b((double)(-j - 1), (double)(8 + i), (double)0.0F).func_181666_a(0.0F, 0.0F, 0.0F, alpha).func_181675_d();
            worldrenderer.func_181662_b((double)(j + 1), (double)(8 + i), (double)0.0F).func_181666_a(0.0F, 0.0F, 0.0F, alpha).func_181675_d();
            worldrenderer.func_181662_b((double)(j + 1), (double)(-1 + i), (double)0.0F).func_181666_a(0.0F, 0.0F, 0.0F, alpha).func_181675_d();
         }

         tessellator.func_78381_a();
         GlStateManager.func_179098_w();
         fontrenderer.func_78276_b(str, -fontrenderer.func_78256_a(str) / 2, i, 553648127);
         GlStateManager.func_179126_j();
         GlStateManager.func_179132_a(true);
         fontrenderer.func_78276_b(str, -fontrenderer.func_78256_a(str) / 2, i, -1);
         GlStateManager.func_179145_e();
         GlStateManager.func_179084_k();
         GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
         GlStateManager.func_179121_F();
      }

   }

   public static void setAlpha(int a) {
      alpha = (float)a / 100.0F;
   }

   public static void setScale(int s) {
      if (s == 0) {
         scale = 0.0F;
      }

      scale = (float)s / 100.0F;
   }

   public static void setSelftag(boolean tag) {
      selftag = tag;
   }

   public static void setOffset(int off) {
      offset = (float)off / 100.0F;
   }

   public static int getOffset() {
      return (int)(offset * 100.0F);
   }

   public static int getScale() {
      return (int)(scale * 100.0F);
   }
}
