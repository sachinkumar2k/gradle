/*
 * Copyright 2024 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.gradle.internal.problems.failure;

import com.google.common.collect.ImmutableList;
import org.jspecify.annotations.Nullable;

import java.util.List;

public class CompositeStackTraceClassifier implements StackTraceClassifier {

    private final List<StackTraceClassifier> classifiers;

    public CompositeStackTraceClassifier(StackTraceClassifier... classifiers) {
        this.classifiers = ImmutableList.copyOf(classifiers);
    }

    @Nullable
    @Override
    public StackTraceRelevance classify(StackTraceElement frame) {
        for (StackTraceClassifier classifier : classifiers) {
            StackTraceRelevance relevance = classifier.classify(frame);
            if (relevance != null) {
                return relevance;
            }
        }
        return null;
    }
}
