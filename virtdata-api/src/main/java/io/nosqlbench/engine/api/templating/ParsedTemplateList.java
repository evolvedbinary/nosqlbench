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

package io.nosqlbench.engine.api.templating;

import io.nosqlbench.virtdata.core.templates.CapturePoint;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.LongFunction;

public class ParsedTemplateList implements LongFunction<List<?>> {
    private final Object[] protolist;
    private final int[] dynamic_idx;
    private final LongFunction<?>[] functions;
    private final List<CapturePoint> captures = new ArrayList<>();

    public ParsedTemplateList(List<Object> list, Map<String, String> bindings, List<Map<String, Object>> cfgsources) {

        List<LongFunction<?>> funcs = new ArrayList<>();
        List<Integer> dindexes = new ArrayList<>();
        protolist = new Object[list.size()];

        for (int i = 0; i < list.size(); i++) {
            Object item = list.get(i);
            Templatizer.Result result = Templatizer.make(bindings, item, null, cfgsources);
            this.captures.addAll(result.getCaptures());

            if (item instanceof String string) {
                switch (result.getType()) {
                    case literal:
                        protolist[i]=string;
                        break;
                    case bindref:
                    case concat:
                        funcs.add(result.getFunction());
                        dindexes.add(i);
                }
            } else if (item instanceof List sublist) {
                ParsedTemplateList listTemplate = new ParsedTemplateList(sublist, bindings, cfgsources);
                if (listTemplate.isStatic()) {
                    protolist[i]=sublist;
                } else {
                    funcs.add(result.getFunction());
                    dindexes.add(i);
                }
            } else if (item instanceof Map submap) {
                ParsedTemplateMap mapTemplate = new ParsedTemplateMap("anonymous", submap, bindings, cfgsources);
                if (mapTemplate.isStatic()) {
                    protolist[i]=submap;
                } else {
                    funcs.add(result.getFunction());
                    dindexes.add(i);
                }
            } else {
                protolist[i]=item;
            }

        }
        this.dynamic_idx = dindexes.stream().mapToInt(Integer::intValue).toArray();
        this.functions = funcs.toArray(new LongFunction<?>[0]);

    }

    @Override
    public List<?> apply(long value) {
        Object[] resultAry=new Object[protolist.length];
        System.arraycopy(protolist,0,resultAry,0,protolist.length);
        for (int i = 0; i < dynamic_idx.length; i++) {
            resultAry[dynamic_idx[i]]=functions[i].apply(value);
        }
        return Arrays.asList(resultAry);
    }

    public boolean isStatic() {
        return dynamic_idx.length==0;
    }

    public List<CapturePoint> getCaptures() {
        return this.captures;
    }
}
