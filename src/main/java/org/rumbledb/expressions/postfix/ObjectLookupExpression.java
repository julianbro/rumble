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

package org.rumbledb.expressions.postfix;


import org.rumbledb.exceptions.ExceptionMetadata;
import org.rumbledb.expressions.Expression;
import org.rumbledb.expressions.Node;

import sparksoniq.semantics.visitor.AbstractNodeVisitor;

import java.util.ArrayList;
import java.util.List;

public class ObjectLookupExpression extends PostfixExpression {

    private Expression lookupExpression;

    public ObjectLookupExpression(Expression mainExpression, Expression lookupExpression, ExceptionMetadata metadata) {
        super(mainExpression, metadata);
        this.lookupExpression = lookupExpression;
    }

    @Override
    public List<Node> getChildren() {
        List<Node> result = new ArrayList<>();
        result.add(this.mainExpression);
        result.add(this.lookupExpression);
        return result;
    }

    public Expression getLookupExpression() {
        return this.lookupExpression;
    }


    @Override
    public String serializationString(boolean prefix) {
        String result = "(objectLookup "
            + this.mainExpression.serializationString(false)
            + " [["
            + this.lookupExpression.serializationString(false)
            + "]])";
        return result;
    }

    @Override
    public <T> T accept(AbstractNodeVisitor<T> visitor, T argument) {
        return visitor.visitObjectLookupExpression(this, argument);
    }
}