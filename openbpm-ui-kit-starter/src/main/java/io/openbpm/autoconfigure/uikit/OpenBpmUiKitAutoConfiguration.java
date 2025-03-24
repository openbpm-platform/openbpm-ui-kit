package io.openbpm.autoconfigure.uikit;

import io.openbpm.uikit.OpenBpmUiKitConfiguration;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Import;

@AutoConfiguration
@Import({OpenBpmUiKitConfiguration.class})
public class OpenBpmUiKitAutoConfiguration {
}

