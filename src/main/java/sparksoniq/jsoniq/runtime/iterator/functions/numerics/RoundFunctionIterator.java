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

package sparksoniq.jsoniq.runtime.iterator.functions.numerics;

import org.rumbledb.api.Item;
import org.rumbledb.exceptions.ExceptionMetadata;
import org.rumbledb.exceptions.IteratorFlowException;
import sparksoniq.jsoniq.ExecutionMode;
import sparksoniq.jsoniq.item.ItemFactory;
import sparksoniq.jsoniq.runtime.iterator.RuntimeIterator;
import sparksoniq.jsoniq.runtime.iterator.functions.base.LocalFunctionCallIterator;
import sparksoniq.semantics.DynamicContext;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class RoundFunctionIterator extends LocalFunctionCallIterator {


    private static final long serialVersionUID = 1L;
    private RuntimeIterator _iterator;

    public RoundFunctionIterator(
            List<RuntimeIterator> arguments,
            ExecutionMode executionMode,
            ExceptionMetadata iteratorMetadata
    ) {
        super(arguments, executionMode, iteratorMetadata);
    }

    @Override
    public void open(DynamicContext context) {
        super.open(context);
        this._iterator = this._children.get(0);
        this._iterator.open(this._currentDynamicContextForLocalExecution);
        this._hasNext = this._iterator.hasNext();
        this._iterator.close();
    }

    @Override
    public Item next() {
        if (this._hasNext) {
            this._hasNext = false;
            Item value = this._iterator.materializeFirstItemOrNull(this._currentDynamicContextForLocalExecution);
            Item precision;
            if (this._children.size() > 1) {
                precision = this._children.get(1)
                    .materializeFirstItemOrNull(this._currentDynamicContextForLocalExecution);
            }
            // if second param is not given precision is set as 0 (rounds to a whole number)
            else {
                precision = ItemFactory.getInstance().createIntegerItem(0);
            }
            try {
                BigDecimal bd = new BigDecimal(value.castToDoubleValue());
                bd = bd.setScale(precision.getIntegerValue(), RoundingMode.HALF_UP);
                return ItemFactory.getInstance().createDoubleItem(bd.doubleValue());

            } catch (IteratorFlowException e) {
                throw new IteratorFlowException(e.getJSONiqErrorMessage(), getMetadata());
            }
        }
        throw new IteratorFlowException(RuntimeIterator.FLOW_EXCEPTION_MESSAGE + " round function", getMetadata());
    }
}
