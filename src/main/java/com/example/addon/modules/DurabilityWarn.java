package io.github.nivalunaa.durabilitywarnaddon.modules;

import io.github.nivalunaa.durabilitywarnaddon.DurabilityWarnAddon;
import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.meteorclient.settings.*;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;

public class DurabilityWarn extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<TargetSlot> target = sgGeneral.add(new EnumSetting.Builder<TargetSlot>()
        .name("target-slot")
        .description("Which item(s) to monitor.")
        .defaultValue(TargetSlot.Armor)
        .build()
    );

    private final Setting<Integer> threshold = sgGeneral.add(new IntSetting.Builder()
        .name("threshold")
        .description("Warn when durability % is below this.")
        .defaultValue(10)
        .min(1)
        .sliderMax(50)
        .build()
    );

    public enum TargetSlot {
        MainHand, OffHand, Helmet, Chestplate, Leggings, Boots, Armor, All
    }

    public DurabilityWarn() {
        super(DurabilityWarnAddon.CATEGORY, "durability-warn", "Live updates in action bar when items are low.");
    }

    @EventHandler
    private void onTick(TickEvent.Post event) {
        if (mc.player == null) return;
        StringBuilder warningMessage = new StringBuilder();

        switch (target.get()) {
            case MainHand -> appendIfLow(mc.player.getMainHandStack(), warningMessage);
            case OffHand  -> appendIfLow(mc.player.getOffHandStack(), warningMessage);

            case Helmet    -> appendIfLow(mc.player.getInventory().getStack(39), warningMessage);
            case Chestplate -> appendIfLow(mc.player.getInventory().getStack(38), warningMessage);
            case Leggings  -> appendIfLow(mc.player.getInventory().getStack(37), warningMessage);
            case Boots     -> appendIfLow(mc.player.getInventory().getStack(36), warningMessage);

            case Armor -> {
                for (int i = 39; i >= 36; i--) {
                    appendIfLow(mc.player.getInventory().getStack(i), warningMessage);
                }
            }
            case All -> {
                appendIfLow(mc.player.getMainHandStack(), warningMessage);
                appendIfLow(mc.player.getOffHandStack(), warningMessage);
                for (int i = 39; i >= 36; i--) {
                    appendIfLow(mc.player.getInventory().getStack(i), warningMessage);
                }
            }
        }

        if (warningMessage.length() > 0) {
            mc.player.sendMessage(Text.literal(warningMessage.toString()), true);
        }
    }

    private void appendIfLow(ItemStack stack, StringBuilder builder) {
        if (stack == null || stack.isEmpty() || !stack.isDamageable()) return;

        int max = stack.getMaxDamage();
        int remaining = max - stack.getDamage();
        double percent = ((double) remaining / max) * 100;

        if (percent <= threshold.get()) {
            if (builder.length() > 0) builder.append(" §7| ");

            String colorCode = stack.getRarity().getFormatting().toString();

            builder.append(colorCode).append(stack.getName().getString())
                   .append("§f: §c").append(remaining).append("§7/").append(max)
                   .append(" §f(§c").append((int) percent).append("%§f)");
        }
    }
}
