package com.example.modules;

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
        .defaultValue("123456")
        .build()
    );

    private int timer = 0;
    private boolean logged = false;

    public AutoLogVuaMC() {
        super(Categories.Misc, "auto-log-vuamc", "Auto login VuaMC");
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
                    mc.player.getInventory().selectedSlot = i;
                    mc.interactionManager.interactItem(mc.player, Hand.MAIN_HAND);
                    break;
                }
            }
        }
    }
                 }
