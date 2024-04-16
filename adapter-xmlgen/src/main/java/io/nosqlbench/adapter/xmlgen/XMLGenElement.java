package io.nosqlbench.adapter.xmlgen;

/*
 * Copyright (c) 2022 nosqlbench
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import java.util.List;
import java.util.Map;

/**
 * The element to add to generated XML
 *
 * @param children
 * @param attrs
 * @param body
 */
public record XMLGenElement(Map<String, Object> children, Map<String, Object> attrs, String body) {
    public XMLGenElement substitute(final List<Object> substitutions) {
        //TODO (AP) substitutions - currently a no-op
        return this;
    }
}
