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

package org.rumbledb.expressions.quantifiers;

import sparksoniq.jsoniq.compiler.translator.metadata.ExpressionMetadata;
import sparksoniq.semantics.visitor.AbstractExpressionOrClauseVisitor;

import java.util.ArrayList;
import java.util.List;

import org.rumbledb.expressions.Expression;
import org.rumbledb.expressions.ExpressionOrClause;

public class QuantifiedExpression extends Expression {
    private final Expression _expression;
    private final QuantifiedOperators _operator;
    private final List<QuantifiedExpressionVar> _variables;


    public QuantifiedExpression(
            QuantifiedOperators operator,
            Expression expression,
            List<QuantifiedExpressionVar> vars,
            ExpressionMetadata metadataFromContext
    ) {
        super(metadataFromContext);
        this._operator = operator;
        this._variables = vars;
        this._expression = expression;
    }

    public ExpressionOrClause getEvaluationExpression() {
        return _expression;
    }

    public QuantifiedOperators getOperator() {
        return _operator;
    }

    public List<QuantifiedExpressionVar> getVariables() {
        return _variables;
    }

    @Override
    public List<ExpressionOrClause> getDescendants(boolean depthSearch) {
        List<ExpressionOrClause> result = new ArrayList<>();
        if (_variables != null)
            _variables.forEach(e -> {
                if (e != null)
                    result.add(e);
            });
        result.add(_expression);
        return getDescendantsFromChildren(result, depthSearch);
    }

    @Override
    public <T> T accept(AbstractExpressionOrClauseVisitor<T> visitor, T argument) {
        return visitor.visitQuantifiedExpression(this, argument);
    }

    @Override
    public String serializationString(boolean prefix) {
        return "";
    }

    public enum QuantifiedOperators {
        EVERY,
        SOME
    }
}