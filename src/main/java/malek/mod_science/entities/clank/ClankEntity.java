package malek.mod_science.entities.clank;

import dev.architectury.utils.NbtType;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Util;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public final class ClankEntity extends PathAwareEntity {
    private static final TrackedData<TrackedClankData> TRACKED_DATA = DataTracker.registerData(ClankEntity.class, Util.make(TrackedClankData.HANDLER, TrackedDataHandlerRegistry::register));

    public ClankEntity(EntityType<? extends ClankEntity> type, World world) {
        super(type, world);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        dataTracker.startTracking(TRACKED_DATA, TrackedClankData.initial(Clanks.CLOCKWORKER));
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        @Nullable TrackedClankData data = dataTracker.get(TRACKED_DATA);
        if (data != null) {
            nbt.put("TrackedData", data.write());
        }
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        if (nbt.contains("TrackedData", NbtType.COMPOUND)) {
            dataTracker.set(TRACKED_DATA, TrackedClankData.create(nbt.getCompound("TrackedData")));
        }
    }
}
