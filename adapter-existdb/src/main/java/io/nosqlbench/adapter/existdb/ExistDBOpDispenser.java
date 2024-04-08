package io.nosqlbench.adapter.existdb;

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


import io.nosqlbench.adapters.api.activityimpl.BaseOpDispenser;
import io.nosqlbench.adapters.api.templating.ParsedOp;

import java.util.function.LongFunction;

public class ExistDBOpDispenser extends BaseOpDispenser<ExistDBOp, ExistDBSpace> {

    private final LongFunction<ExistDBOp> opFunc;

    public ExistDBOpDispenser(ExistDBDriverAdapter adapter, LongFunction<ExistDBSpace> contextFn, ParsedOp op) {
        super(adapter, op);
        opFunc = createOpFunc(contextFn, op);
    }

    private LongFunction<ExistDBOp> createOpFunc(LongFunction<ExistDBSpace> contextFn, ParsedOp op) {

        LongFunction<?> payload = op.getAsRequiredFunction("stmt", Object.class);

        final LongFunction<String> xqueryFn = l -> payload.apply(l).toString();

        final LongFunction<String> collectionFn = op.getAsFunctionOr("collection", "db");

        return l -> new ExistDBOp(
            contextFn.apply(l).getClient(),
            collectionFn.apply(l),
            xqueryFn.apply(l));
    }

    @Override
    public ExistDBOp getOp(long value) {
        return opFunc.apply(value);
    }
}
