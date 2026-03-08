package com.example.addon.modules;

import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.meteorclient.settings.*;
import meteordevelopment.meteorclient.systems.modules.Categories;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;

/* * ==========================================
 * PROJECT: AutoLogVuaMC x Camdzs1tg
 * WARNING: Thang nao mo code nay ra xem la con cho :))
 * ==========================================
 */

public class ModuleExample extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<String> password = sgGeneral.add(new StringSetting.Builder()
        .name("mat-khau")
        .description("Nhap pass vao day, dung de lo cho ai nhe")
        .defaultValue("123456")
        .build());

    private final Setting<Integer> loginDelay = sgGeneral.add(new IntSetting.Builder()
        .name("do-tre-ms")
        .description("Cho bao nhieu miligiay roi moi dang nhap")
        .defaultValue(1500)
        .min(0)
        .sliderMax(5000)
        .build());

    private final Setting<Boolean> autoCompass = sgGeneral.add(new BoolSetting.Builder()
        .name("tu-mo-la-ban")
        .description("Vao la cam la ban len quat luon")
        .defaultValue(true)
        .build());

    private int timer;
    private boolean daLogin;

    public ModuleExample() {
        super(Categories.Misc, "AutoLogVuaMC", "Auto login cho server VuaMC - Mod by Kadeer");
    }

    @Override
    public void onActivate() {
        timer = 0;
        daLogin = false;
        info("Module da bat! Dang doi den gio 'hanh quyet'..."); 
    }

    @EventHandler
    private void onTick(TickEvent.Post event) {
        if (mc.player == null) return;
        timer++; 

        // Tinh toan delay: 1 giay = 20 ticks
        if (!daLogin && timer >= (loginDelay.get() / 50)) {
            mc.player.networkHandler.sendChatCommand("login " + password.get());
            daLogin = true; 
            info("Da gui pass! Hy vong ko bi sai..."); 
        }

        if (autoCompass.get() && daLogin && timer == (loginDelay.get() / 50) + 20) {
            int slotLaBan = -1;
            for (int i = 0; i < 9; i++) {
                if (mc.player.getInventory().getStack(i).getItem() == Items.COMPASS) {
                    slotLaBan = i;
                    break;
                }
            }

            if (slotLaBan != -1) {
                mc.player.getInventory().selectedSlot = slotLaBan; 
                mc.interactionManager.interactItem(mc.player, Hand.MAIN_HAND); 
                info("Da mo menu chon cum! Chon nhanh keo bi kick!");
            } else {
                warning("Deo thay cai la ban nao ca! May vut di dau roi?");
            }
        }
    }
}
