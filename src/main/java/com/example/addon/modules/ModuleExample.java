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
        .description("Nhap pass vao day")
        .defaultValue("123456")
        .build());

    private final Setting<Integer> loginDelay = sgGeneral.add(new IntSetting.Builder()
        .name("do-tre-tick")
        .description("20 ticks = 1 giay")
        .defaultValue(40)
        .min(0)
        .sliderMax(200)
        .build());

    private int timer;
    private boolean daLogin;

    public ModuleExample() {
        super(Categories.Misc, "AutoLogVuaMC", "Auto login VuaMC - Mod by Kadeer");
    }

    @Override
    public void onActivate() {
        timer = 0;
        daLogin = false;
    }

    @EventHandler
    private void onTick(TickEvent.Post event) {
        if (mc.player == null) return;
        timer++;

        if (!daLogin && timer >= loginDelay.get()) {
            mc.player.networkHandler.sendChatCommand("login " + password.get());
            daLogin = true;
            info("Da gui mat khau!");
        }

        if (daLogin && timer == loginDelay.get() + 20) {
            for (int i = 0; i < 9; i++) {
                if (mc.player.getInventory().getStack(i).getItem() == Items.COMPASS) {
                    mc.player.getInventory().selectedSlot = i;
                    mc.interactionManager.interactItem(mc.player, Hand.MAIN_HAND);
                    info("Da mo La Ban!");
                    break;
                }
            }
        }
    }
}
