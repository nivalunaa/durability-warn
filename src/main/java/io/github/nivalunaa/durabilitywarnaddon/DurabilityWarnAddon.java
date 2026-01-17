package io.github.nivalunaa.durabilitywarnaddon;

import io.github.nivalunaa.durabilitywarnaddon.modules.DurabilityWarn;
import com.mojang.logging.LogUtils;
import meteordevelopment.meteorclient.addons.GithubRepo;
import meteordevelopment.meteorclient.addons.MeteorAddon;
import meteordevelopment.meteorclient.systems.modules.Modules; // Keep this
import org.slf4j.Logger;

public class DurabilityWarnAddon extends MeteorAddon {
    public static final Logger LOG = LogUtils.getLogger();

    @Override
    public void onInitialize() {
        LOG.info("Initializing DurabilityWarn Addon");

        // Modules
        Modules.get().add(new DurabilityWarn());
    }

    @Override
    public void onRegisterCategories() {
        // Leave this empty! You aren't adding a new category anymore.
    }

    @Override
    public String getPackage() {
        return "io.github.nivalunaa.durabilitywarnaddon";
    }

    @Override
    public GithubRepo getRepo() {
        return new GithubRepo("nivalunaa", "durability-warn");
    }
}
