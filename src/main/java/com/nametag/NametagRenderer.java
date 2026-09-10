package com.nametag;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.entity.EntityLivingBase;
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
      if (!isPlayer) {
         return; // Spracovávame výhradne hráčov
      }

      if (scale == 0.0F) {
         return;
      }

      y += (double)offset;

      if (canRenderName(entity)) {
         EntityPlayerSP renderPlayer = Minecraft.getMinecraft().thePlayer;
         double d0 = entity.getDistanceSqToEntity(renderPlayer);
         float f = entity.isSneaking() ? RendererLivingEntity.NAME_TAG_RANGE_SNEAK : RendererLivingEntity.NAME_TAG_RANGE;
         if (d0 < (double)(f * f)) {
            String s = entity.getCommandSenderName();
            float f1 = 0.02666667F * scale;
            GL11.glAlphaFunc(516, 0.1F);
            if (entity.isSneaking()) {
               FontRenderer fontrenderer = a.getFontRendererFromRenderManager();
               GL11.glPushMatrix();
               GL11.glTranslatef((float)x, (float)y + entity.height + 0.5F - (entity.isChild() ? entity.height / 2.0F : 0.0F), (float)z);
               GL11.glNormal3f(0.0F, 1.0F, 0.0F);
               GL11.glRotatef(-RenderManager.instance.playerViewY, 0.0F, 1.0F, 0.0F);
               GL11.glRotatef(RenderManager.instance.playerViewX, 1.0F, 0.0F, 0.0F);
               GL11.glScalef(-f1, -f1, f1);
               GL11.glTranslatef(0.0F, 9.374999F, 0.0F);
               GL11.glDisable(2896);
               GL11.glDepthMask(false);
               GL11.glEnable(3042);
               OpenGlHelper.glBlendFunc(770, 771, 1, 0);
               GL11.glDisable(3553);
               int i = fontrenderer.getStringWidth(s) / 2;
               Tessellator tessellator = Tessellator.instance;
               tessellator.startDrawingQuads();
               if (alpha != 0.0F) {
                  tessellator.setColorRGBA_F(0.0F, 0.0F, 0.0F, alpha);
                  tessellator.addVertex((double)(-i - 1), (double)-1.0F, (double)0.0F);
                  tessellator.addVertex((double)(-i - 1), (double)8.0F, (double)0.0F);
                  tessellator.addVertex((double)(i + 1), (double)8.0F, (double)0.0F);
                  tessellator.addVertex((double)(i + 1), (double)-1.0F, (double)0.0F);
               }

               tessellator.draw();
               GL11.glEnable(3553);
               GL11.glDepthMask(true);
               fontrenderer.drawString(s, -fontrenderer.getStringWidth(s) / 2, 0, 553648127);
               GL11.glEnable(2896);
               GL11.glDisable(3042);
               GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
               GL11.glPopMatrix();
            } else {
               playerRenderOffsetLivingLabel(entity, x, y - (entity.isChild() ? (double)(entity.height / 2.0F) : (double)0.0F), z, s, 0.02666667F, d0);
            }
         }
      }

      MinecraftForge.EVENT_BUS.post(new RenderLivingEvent.Specials.Post(entity, a, (double)((float)x), (double)((float)y), (double)((float)z)));
   }

   protected static void playerRenderOffsetLivingLabel(EntityLivingBase entityIn, double x, double y, double z, String str, float p_177069_9_, double p_177069_10_) {
      if (p_177069_10_ < (double)100.0F) {
         Scoreboard scoreboard = ((EntityPlayer)entityIn).getWorldScoreboard();
         ScoreObjective scoreobjective = scoreboard.func_96539_a(2);
         if (scoreobjective != null) {
            Score score = scoreboard.func_96529_a(entityIn.getCommandSenderName(), scoreobjective);
            renderLivingLabel(entityIn, score.getScorePoints() + " " + scoreobjective.getDisplayName(), x, y, z, 64, true);
            y += (double)((float)a.getFontRendererFromRenderManager().FONT_HEIGHT * 1.15F * p_177069_9_);
         }
      }

      renderOffsetLivingLabel(entityIn, x, y, z, str, p_177069_9_, p_177069_10_, true);
   }

   protected static boolean canRenderName(EntityLivingBase entity) {
      return canRenderName2(entity) && entity.hasCustomNameTag();
   }

   protected static boolean canRenderName2(EntityLivingBase entity) {
      if (!(entity instanceof EntityPlayer)) {
         return false; // Ignoruje všetky entity okrem hráčov (vrátane mobov a NPC)
      }

      EntityPlayerSP entityplayersp = Minecraft.getMinecraft().thePlayer;
      if (entity == entityplayersp) {
         return selftag;
      } else {
         Team team = entity.getTeam();
         Team team1 = entityplayersp.getTeam();
         if (team != null && team1 != null) {
            return team.isSameTeam(team1);
         }

         return Minecraft.isGuiEnabled() && !entity.isInvisibleToPlayer(entityplayersp) && entity.ridingEntity == null;
      }
   }

   protected static void renderOffsetLivingLabel(EntityLivingBase entityIn, double x, double y, double z, String str, float p_177069_9_, double p_177069_10_, boolean isPlayer) {
      renderLivingLabel(entityIn, str, x, y, z, 64, isPlayer);
   }

   protected static void renderLivingLabel(EntityLivingBase entityIn, String str, double x, double y, double z, int maxDistance, boolean isPlayer) {
      EntityPlayerSP renderPlayer = Minecraft.getMinecraft().thePlayer;
      double d0 = entityIn.getDistanceSqToEntity(renderPlayer);
      if (d0 <= (double)(maxDistance * maxDistance)) {
         FontRenderer fontrenderer = a.getFontRendererFromRenderManager();
         float f1 = isPlayer ? 0.02666667F * scale : 0.02666667F;
         GL11.glPushMatrix();
         GL11.glTranslatef((float)x + 0.0F, (float)y + entityIn.height + 0.5F, (float)z);
         GL11.glNormal3f(0.0F, 1.0F, 0.0F);
         GL11.glRotatef(-RenderManager.instance.playerViewY, 0.0F, 1.0F, 0.0F);
         GL11.glRotatef(RenderManager.instance.playerViewX, 1.0F, 0.0F, 0.0F);
         GL11.glScalef(-f1, -f1, f1);
         GL11.glDisable(2896);
         GL11.glDepthMask(false);
         GL11.glDisable(2929);
         GL11.glEnable(3042);
         OpenGlHelper.glBlendFunc(770, 771, 1, 0);
         Tessellator tessellator = Tessellator.instance;
         int i = 0;
         if (str.equals("deadmau5")) {
            i = -10;
         }

         int j = fontrenderer.getStringWidth(str) / 2;
         GL11.glDisable(3553);
         tessellator.startDrawingQuads();
         if (alpha != 0.0F) {
            tessellator.setColorRGBA_F(0.0F, 0.0F, 0.0F, alpha);
            tessellator.addVertex((double)(-j - 1), (double)(-1 + i), (double)0.0F);
            tessellator.addVertex((double)(-j - 1), (double)(8 + i), (double)0.0F);
            tessellator.addVertex((double)(j + 1), (double)(8 + i), (double)0.0F);
            tessellator.addVertex((double)(j + 1), (double)(-1 + i), (double)0.0F);
         }

         tessellator.draw();
         GL11.glEnable(3553);
         fontrenderer.drawString(str, -fontrenderer.getStringWidth(str) / 2, i, 553648127);
         GL11.glEnable(2929);
         GL11.glDepthMask(true);
         fontrenderer.drawString(str, -fontrenderer.getStringWidth(str) / 2, i, -1);
         GL11.glEnable(2896);
         GL11.glDisable(3042);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         GL11.glPopMatrix();
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
