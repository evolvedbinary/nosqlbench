/*
 * Copyright (c) 2023-2024 nosqlbench
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

package io.nosqlbench.virtdata.lib.vectors.dnn.euclidean;

import io.nosqlbench.virtdata.api.annotations.Categories;
import io.nosqlbench.virtdata.api.annotations.Category;
import io.nosqlbench.virtdata.api.annotations.ThreadSafeMapper;

import java.util.function.LongFunction;

/**
 * This represents an enumerated population of vectors of some dimension,
 * where any ordinal values which address outside of that enumeration
 * simply wrap with modulo.
 */
@ThreadSafeMapper
@Categories(Category.experimental)
public class DNN_euclidean_v_wrap implements LongFunction<float[]> {

    private final int D;
    private final long N;
    private final double scale;

    public DNN_euclidean_v_wrap(int D, long N, double scale) {
        this.D = D;
        this.N = N;
        this.scale = scale;
    }

    public DNN_euclidean_v_wrap(int D, long N) {
        this(D,N,1.0d);
    }

    @Override
    public float[] apply(long value) {
        value = value % N;
        float[] vector = new float[D];
        for (int idx = 0; idx < vector.length; idx++) {
            vector[idx]= (float)(value+(idx*scale));
        }
        return vector;
    }
}
