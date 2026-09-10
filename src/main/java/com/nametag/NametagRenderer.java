protected static void renderLivingLabel(EntityLivingBase entityIn, String str, double x, double y, double z, int maxDistance) {
      EntityPlayerSP renderPlayer = Minecraft.getMinecraft().thePlayer;
      double d0 = entityIn.getDistanceSqToEntity(renderPlayer);
      if (d0 <= (double)(maxDistance * maxDistance)) {
         FontRenderer fontrenderer = a.getFontRendererFromRenderManager();
         float f1 = 0.02666667F * scale;

         // Default Minecraft 1.7.10 výpočet pre nametag hráča
         double correctY = y + entityIn.height + (double)offset + 0.5D;
         if (entityIn.isSneaking()) {
            correctY -= 0.25D; // Prikrčenie zníži nametag
         }

         GL11.glPushMatrix();
         GL11.glTranslatef((float)x, (float)correctY, (float)z);
         GL11.glNormal3f(0.0F, 1.0F, 0.0F);
         GL11.glRotatef(-RenderManager.instance.playerViewY, 0.0F, 1.0F, 0.0F);
         GL11.glRotatef(RenderManager.instance.playerViewX, 1.0F, 0.0F, 0.0F);
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
