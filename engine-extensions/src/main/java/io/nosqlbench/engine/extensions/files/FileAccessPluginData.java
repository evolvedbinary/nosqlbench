/*
 * Copyright (c) 2022-2023 nosqlbench
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.nosqlbench.engine.extensions.files;

import com.codahale.metrics.MetricRegistry;
import io.nosqlbench.api.config.LabeledScenarioContext;
import io.nosqlbench.api.extensions.ComputeFunctionsPluginInfo;
import io.nosqlbench.nb.annotations.Service;
import org.apache.logging.log4j.Logger;

@Service(value = ComputeFunctionsPluginInfo.class, selector = "files")
public class FileAccessPluginData implements ComputeFunctionsPluginInfo<FileAccess> {

    @Override
    public String getDescription() {
        return "Allows for convenient read access to local files";
    }

    @Override
    public FileAccess getExtensionObject(final Logger logger, final MetricRegistry metricRegistry, final LabeledScenarioContext scriptContext) {
        return new FileAccess();
    }

}
