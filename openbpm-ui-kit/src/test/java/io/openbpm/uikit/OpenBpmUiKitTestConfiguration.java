/*
 * Copyright (c) Haulmont 2025. All Rights Reserved.
 * Use is subject to license terms.
 */

package io.openbpm.uikit;

import io.jmix.core.annotation.JmixModule;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Import;

@SpringBootConfiguration
@EnableAutoConfiguration
@Import(OpenBpmUiKitConfiguration.class)
@JmixModule(id = "io.openbpm.uikit.test", dependsOn = OpenBpmUiKitConfiguration.class)
public class OpenBpmUiKitTestConfiguration {

}
