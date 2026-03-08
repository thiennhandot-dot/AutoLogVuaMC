package com.example.addon.modules;

import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.settings.StringSetting;
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
        .description("Mat khau cua ban")
        .defaultValue("123456")
        .build()
    );

    private int timer;
    private boolean daLogin;

    public ModuleExample() {
        super(Categories.Misc, "AutoLogVuaMC", "Auto login - Mod by Kadeer");
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

        // Su dung ChatUtils cua Meteor de KHONG BI LOI phien ban MC
        if (!daLogin && timer >= 60) {
            ChatUtils.sendPlayerMsg("/login " + password.get());
            daLogin = true;
        }

        // Sau khi login 2 giay, tu dong tim va bam La ban
        if (daLogin && timer == 100) {
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
