package com.example.addon.modules;

import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.meteorclient.settings.*;
import meteordevelopment.meteorclient.systems.modules.Categories;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;

public class AutoLogVuaMC extends Module {

    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<String> password = sgGeneral.add(new StringSetting.Builder()
        .name("password")
        .description("Password để login")
        .defaultValue("123456")
        .build()
    );

    private int timer;
    private boolean logged;

    public AutoLogVuaMC() {
        super(Categories.Misc, "auto-log-vuamc", "Tự động login và mở compass server VuaMC");
    }

    @Override
    public void onActivate() {
        timer = 0;
        logged = false;
    }

    @EventHandler
    private void onTick(TickEvent.Post event) {
        if (mc.player == null) return;

        timer++;

        if (!logged && timer >= 60) {
            mc.player.networkHandler.sendChatCommand("login " + password.get());
            logged = true;
        }

        if (logged && timer == 100) {
            for (int i = 0; i < 9; i++) {
                if (mc.player.getInventory().getStack(i).getItem() == Items.COMPASS) {

                    mc.player.getInventory().setSelectedSlot(i);
                    mc.interactionManager.interactItem(mc.player, Hand.MAIN_HAND);

                    break;
                }
            }
        }
    }
}
