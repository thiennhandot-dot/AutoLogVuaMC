package com.example.addon.modules;

import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.meteorclient.settings.*;
import meteordevelopment.meteorclient.systems.modules.Categories;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;

public class ModuleExample extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<String> password = sgGeneral.add(new StringSetting.Builder()
        .name("password")
        .description("Password để tự động login.")
        .defaultValue("123456")
        .build()
    );

    private int timer = 0;
    private boolean loggedIn = false;

    public ModuleExample() {
        super(Categories.Misc, "AutoLogVuaMC", "Tự động login và mở compass.");
    }

    @Override
    public void onActivate() {
        timer = 0;
        loggedIn = false;
    }

    @EventHandler
    private void onTick(TickEvent.Post event) {
        if (mc.player == null || mc.world == null) return;

        timer++;

        // Auto login sau ~3 giây
        if (!loggedIn && timer >= 60) {
            mc.player.networkHandler.sendChatCommand("login " + password.get());
            loggedIn = true;
        }

        // Mở compass sau khi login
        if (loggedIn && timer >= 100) {
            for (int i = 0; i < 9; i++) {
                if (mc.player.getInventory().getStack(i).getItem() == Items.COMPASS) {
                    mc.player.getInventory().selectedSlot = i;

                    if (mc.interactionManager != null) {
                        mc.interactionManager.interactItem(mc.player, Hand.MAIN_HAND);
                    }

                    toggle(); // tắt module sau khi dùng
                    break;
                }
            }
        }
    }
                        }
