/*
 * Copyright (c) Haulmont 2025. All Rights Reserved.
 * Use is subject to license terms.
 */

package io.openbpm.uikit;

import io.jmix.core.CoreConfiguration;
import io.jmix.core.annotation.JmixModule;
import io.jmix.flowui.FlowuiConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
@JmixModule(dependsOn = {CoreConfiguration.class, FlowuiConfiguration.class})
public class OpenBpmUiKitConfiguration {

}
