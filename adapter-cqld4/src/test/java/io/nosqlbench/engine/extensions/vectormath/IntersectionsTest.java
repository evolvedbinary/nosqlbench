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

package io.nosqlbench.engine.extensions.vectormath;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
class IntersectionsTest {

    @Test
    public void testIntegerIntersection() {
        int[] result = Intersections.find(new int[]{1,2,3,4,5},new int[]{4,5,6,7,8});
        assertThat(result).isEqualTo(new int[]{4,5});
    }

    @Test
    public void testLongIntersection() {
        long[] result = Intersections.find(new long[]{1,2,3,4,5},new long[]{4,5,6,7,8});
        assertThat(result).isEqualTo(new long[]{4,5});
    }

    @Test
    public void testCountIntIntersection() {
        long result = Intersections.count(new int[]{1,3,5,7,9}, new int[]{1,2,3,9,10});
        assertThat(result).isEqualTo(3L);
    }
    @Test
    public void testCountLongIntersection() {
        long result = Intersections.count(new long[]{1,3,5,7,9}, new long[]{1,2,3,9,10});
        assertThat(result).isEqualTo(3);
    }

}