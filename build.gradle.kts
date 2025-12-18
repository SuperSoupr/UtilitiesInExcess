
plugins {
    id("com.gtnewhorizons.gtnhconvention")
}

tasks.jar {
    manifest {
        attributes["MixinConfigs"] = "mixins.utilitiesinexcess.preinit.json"
    }
}
