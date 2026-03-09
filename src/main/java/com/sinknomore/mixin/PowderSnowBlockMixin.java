package com.sinknomore.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.PowderSnowBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PowderSnowBlock.class)
public class PowderSnowBlockMixin {

    // FIX 1: Allow walking on top (Prevents vertical sinking)
    @Inject(method = "canWalkOnPowderSnow", at = @At("HEAD"), cancellable = true)
    private static void stopSinking(Entity entity, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(true);
    }

    // FIX 2: Give the block a solid hitbox (Prevents walking through sides)
    @Inject(method = "getCollisionShape", at = @At("HEAD"), cancellable = true)
    private void makeItSolid(BlockState state, BlockView world, BlockPos pos, ShapeContext context, CallbackInfoReturnable<VoxelShape> cir) {
        // This tells the game the block is a full, solid cube
        cir.setReturnValue(VoxelShapes.fullCube());
    }
}