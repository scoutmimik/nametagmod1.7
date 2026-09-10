package fiw.nametageditor;

import java.io.IOException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.fml.client.config.GuiSlider;

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

   public void func_73866_w_() {
      int buttonLength = 180;
      this.centerButtons(5, buttonLength);
      this.field_146292_n.add(this.buttonSelfTag = new GuiButton(1, this.positionX, this.positionY[0], buttonLength, 20, "Own nametag: " + this.getSelfTag()));
      this.field_146292_n.add(this.alpha = new GuiSlider(7, this.positionX, this.positionY[1], buttonLength, 20, "Background alpha: ", "", (double)0.0F, (double)25.0F, (double)(NametagRenderer.alpha * 100.0F), false, true));
      this.field_146292_n.add(this.sliderOffset = new GuiSlider(2, this.positionX, this.positionY[3], buttonLength, 20, "Y Offset: ", "", (double)-20.0F, (double)0.0F, (double)NametagRenderer.getOffset(), false, true));
      this.field_146292_n.add(this.sliderScale = new GuiSlider(3, this.positionX, this.positionY[4], buttonLength, 20, "Scale: ", "%", (double)0.0F, (double)100.0F, (double)NametagRenderer.getScale(), false, true));
   }

   private String getSelfTag() {
      return NametagRenderer.selftag ? EnumChatFormatting.DARK_GREEN + "enabled" : EnumChatFormatting.RED + "disabled";
   }

   public void centerButtons(int amount, int buttonLength) {
      this.positionX = this.field_146294_l / 2 - buttonLength / 2;
      this.positionY = new Integer[amount];
      int center = (this.field_146295_m + amount * 24) / 2;
      int buttonStarts = center - amount * 24;

      for(int i = 0; i != amount; ++i) {
         this.positionY[i] = buttonStarts + 24 * i;
      }

   }

   public void func_73863_a(int mouseX, int mouseY, float ticks) {
      this.func_146276_q_();
      super.func_73863_a(mouseX, mouseY, ticks);
      super.func_73732_a(Minecraft.func_71410_x().field_71466_p, "Player nametags", this.field_146294_l / 2, this.positionY[2] + 7, 16777215);
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

   protected void func_146284_a(GuiButton button) throws IOException {
      switch (button.field_146127_k) {
         case 1:
            NametagRenderer.selftag = !NametagRenderer.selftag;
            button.field_146126_j = "Own nametag: " + this.getSelfTag();
         default:
      }
   }

   public void func_146281_b() {
      Main.config.get("Background", "Alpha", 25).set(this.alpha.getValueInt());
      Main.config.get("Player nametags", "Scale", 100).set(this.sliderScale.getValueInt());
      Main.config.get("Player nametags", "Y offset", 0).set(this.sliderOffset.getValueInt());
      Main.config.get("Other", "Own nametag", false).set(NametagRenderer.selftag);
      Main.config.save();
   }
}
