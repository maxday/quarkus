package io.quarkus.flyway.devconsole;

import static io.quarkus.deployment.annotations.ExecutionTime.RUNTIME_INIT;

import io.quarkus.deployment.IsDevelopment;
import io.quarkus.deployment.annotations.BuildStep;
import io.quarkus.deployment.annotations.Record;
import io.quarkus.devconsole.spi.DevConsoleRouteBuildItem;
import io.quarkus.devconsole.spi.DevConsoleRuntimeTemplateInfoBuildItem;
import io.quarkus.flyway.runtime.FlywayContainerSupplier;
import io.quarkus.flyway.runtime.devconsole.FlywayDevConsoleRecorder;

public class DevConsoleProcessor {

    @BuildStep(onlyIf = IsDevelopment.class)
    public DevConsoleRuntimeTemplateInfoBuildItem collectBeanInfo() {
        return new DevConsoleRuntimeTemplateInfoBuildItem("containers", new FlywayContainerSupplier());
    }

    @BuildStep
    @Record(value = RUNTIME_INIT, optional = true)
    DevConsoleRouteBuildItem invokeEndpoint(FlywayDevConsoleRecorder recorder) {
        return new DevConsoleRouteBuildItem("containers", "POST", recorder.handler());
    }
}
