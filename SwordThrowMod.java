package com.example.swordthrow;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.client.event.InputEvent;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.phys.Vec3;
import org.lwjgl.glfw.GLFW;

@Mod(SwordThrowMod.MODID)
public class SwordThrowMod {
    public static final String MODID = "swordthrowmod";

    public static KeyMapping throwKey;

    public SwordThrowMod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        modEventBus.addListener(this::clientSetup);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        throwKey = new KeyMapping("key.swordthrow.throw", GLFW.GLFW_KEY_R, "key.categories.misc");
        net.minecraftforge.client.ClientRegistry.registerKeyBinding(throwKey);
    }

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        if (throwKey.isDown()) {
            throwSword();
        }
    }

    private void throwSword() {
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;
        if (player != null) {
            ItemStack heldItem = player.getMainHandItem();
            if (heldItem.getItem() instanceof SwordItem) {
                // Simple implementation: drop the sword
                player.drop(heldItem, false);
                player.setItemInHand(player.getUsedItemHand(), ItemStack.EMPTY);
            }
        }
    }
}