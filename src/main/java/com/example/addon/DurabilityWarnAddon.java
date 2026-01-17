package io.github.nivalunaa.durabilitywarnaddon;

import io.github.nivalunaa.durabilitywarnaddon.modules.DurabilityWarn;
import com.mojang.logging.LogUtils;
import meteordevelopment.meteorclient.addons.GithubRepo;
import meteordevelopment.meteorclient.addons.MeteorAddon;
import meteordevelopment.meteorclient.systems.modules.Category;
import meteordevelopment.meteorclient.systems.modules.Modules;
import org.slf4j.Logger;

public class DurabilityWarnAddon extends MeteorAddon {
    public static final Logger LOG = LogUtils.getLogger();
    public static final Category CATEGORY = new Category("Example");

    @Override
    public void onInitialize() {
        LOG.info("Initializing DurabilityWarn Addon");

        // Modules
        Modules.get().add(new DurabilityWarn());
    }

    @Override
    public void onRegisterCategories() {
        Modules.registerCategory(CATEGORY);
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
