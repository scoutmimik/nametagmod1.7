package com.nametag;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.EnumChatFormatting;
import cpw.mods.fml.client.config.GuiSlider;

public class GUIMain extends GuiScreen {
   private GuiButton buttonSelfTag;
   private GuiButton buttonBackgroundColor;
   private GuiSlider sliderOffset;
   private GuiSlider sliderScale;
   private GuiSlider red;
   private GuiSlider green;
   private GuiSlider blue;
   private GuiSlider alpha;
   private Integer[] positionY;
   private int positionX;

   public void initGui() {
      int buttonLength = 180;
      this.centerButtons(5, buttonLength);
      this.buttonList.add(this.buttonSelfTag = new GuiButton(1, this.positionX, this.positionY[0], buttonLength, 20, "Own nametag: " + this.getSelfTag()));
      this.buttonList.add(this.alpha = new GuiSlider(7, this.positionX, this.positionY[1], buttonLength, 20, "Background alpha: ", "", (double)0.0F, (double)25.0F, (double)(NametagRenderer.alpha * 100.0F), false, true));
      this.buttonList.add(this.sliderOffset = new GuiSlider(2, this.positionX, this.positionY[3], buttonLength, 20, "Y Offset: ", "", (double)-20.0F, (double)0.0F, (double)NametagRenderer.getOffset(), false, true));
      this.buttonList.add(this.sliderScale = new GuiSlider(3, this.positionX, this.positionY[4], buttonLength, 20, "Scale: ", "%", (double)0.0F, (double)100.0F, (double)NametagRenderer.getScale(), false, true));
   }

   private String getSelfTag() {
      return NametagRenderer.selftag ? EnumChatFormatting.DARK_GREEN + "enabled" : EnumChatFormatting.RED + "disabled";
   }

   public void centerButtons(int amount, int buttonLength) {
      this.positionX = this.width / 2 - buttonLength / 2;
      this.positionY = new Integer[amount];
      int center = (this.height + amount * 24) / 2;
      int buttonStarts = center - amount * 24;

      for(int i = 0; i != amount; ++i) {
         this.positionY[i] = buttonStarts + 24 * i;
      }
   }

   public void drawScreen(int mouseX, int mouseY, float ticks) {
      this.drawDefaultBackground();
      super.drawScreen(mouseX, mouseY, ticks);
      this.drawCenteredString(Minecraft.getMinecraft().fontRendererObj, "Player nametags", this.width / 2, this.positionY[2] + 7, 16777215);
      if (this.sliderOffset.dragging) {
         NametagRenderer.setOffset(this.sliderOffset.getValueInt());
      }

      if (this.alpha.dragging) {
         NametagRenderer.setAlpha(this.alpha.getValueInt());
      }

      if (this.sliderScale.dragging) {
         NametagRenderer.setScale(this.sliderScale.getValueInt());
      }
   }

   protected void actionPerformed(GuiButton button) {
      switch (button.id) {
         case 1:
            NametagRenderer.selftag = !NametagRenderer.selftag;
            button.displayString = "Own nametag: " + this.getSelfTag();
            break;
         default:
      }
   }

   public void onGuiClosed() {
      Main.config.get("Background", "Alpha", 25).set(this.alpha.getValueInt());
      Main.config.get("Player nametags", "Scale", 100).set(this.sliderScale.getValueInt());
      Main.config.get("Player nametags", "Y offset", 0).set(this.sliderOffset.getValueInt());
      Main.config.get("Other", "Own nametag", false).set(NametagRenderer.selftag);
      Main.config.save();
   }
}
