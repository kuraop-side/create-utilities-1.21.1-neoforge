package me.duquee.createutilities;

import com.simibubi.create.foundation.data.CreateRegistrate;
import me.duquee.createutilities.blocks.CUBlocks;
import me.duquee.createutilities.blocks.CUTileEntities;
import me.duquee.createutilities.blocks.voidtypes.CUContainerTypes;
import me.duquee.createutilities.blocks.voidtypes.battery.VoidBatteryData;
import me.duquee.createutilities.blocks.voidtypes.chest.VoidChestInventoriesData;
import me.duquee.createutilities.blocks.voidtypes.motor.VoidMotorNetworkHandler;
import me.duquee.createutilities.blocks.voidtypes.tank.VoidTanksData;
import me.duquee.createutilities.items.CUItems;
import me.duquee.createutilities.mountedstorage.CUMountedStorages;
import me.duquee.createutilities.networking.CUPackets;
import me.duquee.createutilities.tabs.CUCreativeTabs;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.ModLoadingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod(CreateUtilities.ID)
public class CreateUtilities {

	public static final String ID = "createutilities";
	public static final String NAME = "Create Utilities";
	public static final Logger LOGGER = LoggerFactory.getLogger(NAME);

	public static final CreateRegistrate REGISTRATE = CreateRegistrate.create(ID);

	public static final VoidMotorNetworkHandler VOID_MOTOR_LINK_NETWORK_HANDLER = new VoidMotorNetworkHandler();
	public static VoidChestInventoriesData VOID_CHEST_INVENTORIES_DATA;

	public static VoidTanksData VOID_TANKS_DATA;
	public static VoidBatteryData VOID_BATTERIES_DATA;

	public CreateUtilities() {
		onCtor();
	}

	public static void onCtor() {

               IEventBus modEventBus = ModLoadingContext.get().getActiveContainer().getEventBus();
		IEventBus gameEventBus = NeoForge.EVENT_BUS;

		REGISTRATE.registerEventListeners(modEventBus);

		CUBlocks.register();
		CUItems.register();
		CUTileEntities.register();
		CUContainerTypes.register();
		CUCreativeTabs.register(modEventBus);
		CUMountedStorages.register();

		modEventBus.addListener(CreateUtilities::init);
        if (FMLEnvironment.dist.isClient()) {
            CreateUtilitiesClient.onCtorClient(modEventBus, gameEventBus);
        }

	}

	public static void init(final FMLCommonSetupEvent event) {
		CUPackets.registerPackets();
	}

	public static ResourceLocation asResource(String path) {
		return new ResourceLocation(ID, path);
	}
}
