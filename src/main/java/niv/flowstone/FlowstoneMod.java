package niv.flowstone;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.fabricmc.api.ModInitializer;

public class FlowstoneMod implements ModInitializer {

    public static final Logger log = LogManager.getLogger();

    public static final String MOD_ID = "flowstone";
    public static final String MOD_NAME = "Flowstone";

    @Override
    public void onInitialize() {
        log.info("[{}] Initializing", MOD_NAME);
    }

}
