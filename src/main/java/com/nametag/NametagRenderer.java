package com.nametag;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
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

      if (entity instanceof EntityArmorStand && entity.isChild() || canRenderName(entity)) {
         double d0 = entity.getDistanceSqToEntity(a.getRenderManager().livingPlayer);
         float f = entity.isSneaking() ? RendererLivingEntity.NAME_TAG_RANGE_SNEAK : RendererLivingEntity.NAME_TAG_RANGE;
         if (d0 < (double)(f * f)) {
            String s = entity.getDisplayName().getFormattedText();
            float f1 = isPlayer ? 0.02666667F * scale : 0.02666667F;
            GL11.glAlphaFunc(516, 0.1F);
            if (entity.isSneaking()) {
               FontRenderer fontrenderer = a.getFontRendererFromManager();
               GL11.glPushMatrix();
               GL11.glTranslatef((float)x, (float)y + entity.height + 0.5F - (entity.isChild() ? entity.height / 2.0F : 0.0F), (float)z);
               GL11.glNormal3f(0.0F, 1.0F, 0.0F);
               GL11.glRotatef(-a.getRenderManager().playerViewY, 0.0F, 1.0F, 0.0F);
               GL11.glRotatef(a.getRenderManager().playerViewX, 1.0F, 0.0F, 0.0F);
               GL11.glScalef(-f1, -f1, f1);
               GL11.glTranslatef(0.0F, 9.374999F, 0.0F);
               GL11.glDisable(GL11.GL_LIGHTING);
               GL11.glDepthMask(false);
               GL11.glEnable(GL11.GL_BLEND);
               OpenGlHelper.glBlendFunc(770, 771, 1, 0);
               GL11.glDisable(GL11.GL_TEXTURE_2D);
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
               GL11.glEnable(GL11.GL_TEXTURE_2D);
               GL11.glDepthMask(true);
               fontrenderer.drawString(s, -fontrenderer.getStringWidth(s) / 2, 0, 553648127);
               GL11.glEnable(GL11.GL_LIGHTING);
               GL11.glDisable(GL11.GL_BLEND);
               GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
               GL11.glPopMatrix();
            } else if (isPlayer) {
               playerRenderOffsetLivingLabel(entity, x, y - (entity.isChild() ? (double)(entity.height / 2.0F) : (double)0.0F), z, s, 0.02666667F, d0);
            } else {
               renderOffsetLivingLabel(entity, x, y - (entity.isChild() ? (double)(entity.height / 2.0F) : (double)0.0F), z, s, 0.02666667F, d0, false);
            }
         }
      }

      MinecraftForge.EVENT_BUS.post(new RenderLivingEvent.Specials.Post(entity, a, (float)x, (float)y, (float)z));
   }

   protected static void playerRenderOffsetLivingLabel(EntityLivingBase entityIn, double x, double y, double z, String str, float p_177069_9_, double p_177069_10_) {
      if (p_177069_10_ < (double)100.0F) {
         Scoreboard scoreboard = ((EntityPlayer)entityIn).getWorldScoreboard();
         ScoreObjective scoreobjective = scoreboard.func_96539_a(2);
         if (scoreobjective != null) {
            Score score = scoreboard.getValueFromObjective(entityIn.getCommandSenderName(), scoreobjective);
            renderLivingLabel(entityIn, score.getScorePoints() + " " + scoreobjective.getDisplayName(), x, y, z, 64, true);
            y += (double)((float)a.getFontRendererFromManager().FONT_HEIGHT * 1.15F * p_177069_9_);
         }
      }

      renderOffsetLivingLabel(entityIn, x, y, z, str, p_177069_9_, p_177069_10_, true);
   }

   protected static boolean canRenderName(EntityLivingBase entity) {
      return canRenderName2(entity) && (entity.getAlwaysRenderNameTagForRender() || entity.hasCustomNameTag() && entity == a.getRenderManager().livingPlayer);
   }

   protected static boolean canRenderName2(EntityLivingBase entity) {
      if (entity == a.getRenderManager().livingPlayer) {
         return selftag;
      } else {
         EntityPlayerSP entityplayersp = Minecraft.getMinecraft().thePlayer;
         if (entity instanceof EntityPlayer && entity != entityplayersp) {
            Team team = entity.getTeam();
            Team team1 = entityplayersp.getTeam();
            if (team != null) {
               Team.EnumVisible team$enumvisible = team.func_178770_i();
               switch (team$enumvisible) {
                  case ALWAYS:
                     return true;
                  case NEVER:
                     return false;
                  case HIDE_FOR_OTHER_TEAMS:
                     return team1 == null || team.isSameTeam(team1);
                  case HIDE_FOR_OWN_TEAM:
                     return team1 == null || !team.isSameTeam(team1);
                  default:
                     return true;
               }
            }
         }

         return Minecraft.isGuiEnabled() && !entity.isInvisibleToPlayer(entityplayersp) && entity.riddenByEntity == null;
      }
   }

   protected static void renderOffsetLivingLabel(EntityLivingBase entityIn, double x, double y, double z, String str, float p_177069_9_, double p_177069_10_, boolean isPlayer) {
      renderLivingLabel(entityIn, str, x, y, z, 64, isPlayer);
   }

   protected static void renderLivingLabel(EntityLivingBase entityIn, String str, double x, double y, double z, int maxDistance, boolean isPlayer) {
      double d0 = entityIn.getDistanceSqToEntity(a.getRenderManager().livingPlayer);
      if (d0 <= (double)(maxDistance * maxDistance)) {
         FontRenderer fontrenderer = a.getFontRendererFromManager();
         float f1 = isPlayer ? 0.02666667F * scale : 0.02666667F;
         GL11.glPushMatrix();
         GL11.glTranslatef((float)x + 0.0F, (float)y + entityIn.height + 0.5F, (float)z);
         GL11.glNormal3f(0.0F, 1.0F, 0.0F);
         GL11.glRotatef(-a.getRenderManager().playerViewY, 0.0F, 1.0F, 0.0F);
         GL11.glRotatef(a.getRenderManager().playerViewX, 1.0F, 0.0F, 0.0F);
         GL11.glScalef(-f1, -f1, f1);
         GL11.glDisable(GL11.GL_LIGHTING);
         GL11.glDepthMask(false);
         GL11.glDisable(GL11.GL_DEPTH_TEST);
         GL11.glEnable(GL11.GL_BLEND);
         OpenGlHelper.glBlendFunc(770, 771, 1, 0);
         Tessellator tessellator = Tessellator.instance;
         int i = 0;
         if (str.equals("deadmau5")) {
            i = -10;
         }

         int j = fontrenderer.getStringWidth(str) / 2;
         GL11.glDisable(GL11.GL_TEXTURE_2D);
         tessellator.startDrawingQuads();
         if (alpha != 0.0F) {
            tessellator.setColorRGBA_F(0.0F, 0.0F, 0.0F, alpha);
            tessellator.addVertex((double)(-j - 1), (double)(-1 + i), (double)0.0F);
            tessellator.addVertex((double)(-j - 1), (double)(8 + i), (double)0.0F);
            tessellator.addVertex((double)(j + 1), (double)(8 + i), (double)0.0F);
            tessellator.addVertex((double)(j + 1), (double)(-1 + i), (double)0.0F);
         }

         tessellator.draw();
         GL11.glEnable(GL11.GL_TEXTURE_2D);
         fontrenderer.drawString(str, -fontrenderer.getStringWidth(str) / 2, i, 553648127);
         GL11.glEnable(GL11.GL_DEPTH_TEST);
         GL11.glDepthMask(true);
         fontrenderer.drawString(str, -fontrenderer.getStringWidth(str) / 2, i, -1);
         GL11.glEnable(GL11.GL_LIGHTING);
         GL11.glDisable(GL11.GL_BLEND);
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
