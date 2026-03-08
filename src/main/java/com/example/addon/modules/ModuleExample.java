package com.example.addon.modules;

import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.meteorclient.settings.*;
import meteordevelopment.meteorclient.systems.modules.Categories;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.utils.player.ChatUtils;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;

public class ModuleExample extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<String> password = sgGeneral.add(new StringSetting.Builder()
        .name("mat-khau")
        .defaultValue("123456")
        .build()
    );

    private int timer;
    private boolean daLogin;

    public ModuleExample() {
        super(Categories.Misc, "AutoLogVuaMC", "Mod by Kadeer");
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

        // Tu dong dang nhap sau 3 giay
        if (!daLogin && timer >= 60) {
            ChatUtils.sendPlayerMsg("/login " + password.get());
            daLogin = true;
        }

        // Tu dong mo La ban sau khi login
        if (daLogin && timer == 120) {
            for (int i = 0; i < 9; i++) {
                if (mc.player.getInventory().getStack(i).getItem() == Items.COMPASS) {
                    mc.player.getInventory().selectedSlot = i;
                    if (mc.interactionManager != null) {
                        mc.interactionManager.interactItem(mc.player, Hand.MAIN_HAND);
                    }
                    break;
                }
            }
        }
    }
}
