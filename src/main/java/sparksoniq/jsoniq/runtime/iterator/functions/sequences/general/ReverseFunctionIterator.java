/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Authors: Stefan Irimescu, Can Berker Cikis
 *
 */

package sparksoniq.jsoniq.runtime.iterator.functions.sequences.general;

import org.rumbledb.api.Item;
import org.rumbledb.exceptions.ExceptionMetadata;
import org.rumbledb.exceptions.IteratorFlowException;
import sparksoniq.jsoniq.ExecutionMode;
import sparksoniq.jsoniq.runtime.iterator.RuntimeIterator;
import sparksoniq.jsoniq.runtime.iterator.functions.base.LocalFunctionCallIterator;
import sparksoniq.semantics.DynamicContext;

import java.util.ArrayList;
import java.util.List;

public class ReverseFunctionIterator extends LocalFunctionCallIterator {


    private static final long serialVersionUID = 1L;
    private List<Item> results;
    private int currentIndex = 0;

    public ReverseFunctionIterator(
            List<RuntimeIterator> parameters,
            ExecutionMode executionMode,
            ExceptionMetadata iteratorMetadata
    ) {
        super(parameters, executionMode, iteratorMetadata);
    }

    @Override
    public Item next() {
        if (this.hasNext()) {
            return getResult();
        }
        throw new IteratorFlowException(FLOW_EXCEPTION_MESSAGE + "reverse function", getMetadata());
    }

    public Item getResult() {
        if (this.results == null || this.results.size() == 0) {
            throw new IteratorFlowException("getResult called on an empty list of results", getMetadata());
        }
        if (this.currentIndex == this.results.size() - 1)
            this.hasNext = false;
        return this.results.get(this.currentIndex++);
    }

    @Override
    public void open(DynamicContext context) {
        super.open(context);
        this.results = new ArrayList<>();
        this.currentIndex = 0;

        RuntimeIterator sequenceIterator = this.children.get(0);

        List<Item> items = sequenceIterator.materialize(this.currentDynamicContextForLocalExecution);

        for (int i = items.size() - 1; i >= 0; i--) {
            this.results.add(items.get(i));
        }

        this.hasNext = this.results.size() != 0;
    }
}
