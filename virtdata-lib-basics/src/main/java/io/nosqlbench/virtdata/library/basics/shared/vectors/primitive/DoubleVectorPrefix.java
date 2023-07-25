/*
 * Copyright (c) 2023 nosqlbench
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

package io.nosqlbench.virtdata.library.basics.shared.vectors.primitive;

import io.nosqlbench.virtdata.api.annotations.Categories;
import io.nosqlbench.virtdata.api.annotations.Category;
import io.nosqlbench.virtdata.api.annotations.ThreadSafeMapper;

import java.util.function.Function;

/**
 * Prefix the incoming array with an empty double[] of the given size.
 */
@ThreadSafeMapper
@Categories({Category.experimental,Category.vectors})
public class DoubleVectorPrefix implements Function<double[],double[]> {

    private final int size;

    public DoubleVectorPrefix(int size) {
        this.size = size;
    }

    @Override
    public double[] apply(double[] doubles) {
        double[] newary = new double[size+doubles.length];
        System.arraycopy(doubles,0,newary,size,doubles.length);
        return newary;
    }
}
