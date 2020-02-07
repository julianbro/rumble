package sparksoniq.jsoniq.runtime.iterator.functions.datetime.components;

import org.joda.time.DateTimeZone;
import org.joda.time.Instant;
import org.rumbledb.api.Item;
import org.rumbledb.exceptions.ExceptionMetadata;
import org.rumbledb.exceptions.InvalidTimezoneException;
import org.rumbledb.exceptions.IteratorFlowException;
import sparksoniq.jsoniq.ExecutionMode;
import sparksoniq.jsoniq.item.ItemFactory;
import sparksoniq.jsoniq.runtime.iterator.RuntimeIterator;
import sparksoniq.jsoniq.runtime.iterator.functions.base.LocalFunctionCallIterator;
import sparksoniq.semantics.DynamicContext;

import java.util.List;

public class AdjustTimeToTimezone extends LocalFunctionCallIterator {

    private static final long serialVersionUID = 1L;
    private Item _timeItem = null;
    private Item _timezone = null;

    public AdjustTimeToTimezone(
            List<RuntimeIterator> arguments,
            ExecutionMode executionMode,
            ExceptionMetadata iteratorMetadata
    ) {
        super(arguments, executionMode, iteratorMetadata);
    }

    @Override
    public Item next() {
        if (this._hasNext) {
            this._hasNext = false;
            if (this._timezone == null && this._children.size() == 1)
                return ItemFactory.getInstance()
                    .createTimeItem(this._timeItem.getDateTimeValue().withZone(DateTimeZone.UTC), true);
            if (this._timezone == null) {
                if (this._timeItem.hasTimeZone())
                    return ItemFactory.getInstance()
                        .createTimeItem(
                            this._timeItem.getDateTimeValue()
                                .withZoneRetainFields(this._timeItem.getDateTimeValue().getZone()),
                            false
                        );
                return ItemFactory.getInstance()
                    .createTimeItem(this._timeItem.getDateTimeValue(), this._timeItem.hasTimeZone());
            } else {
                if (this.checkTimeZoneArgument())
                    throw new InvalidTimezoneException("Invalid timezone", getMetadata());
                if (this._timeItem.hasTimeZone())
                    return ItemFactory.getInstance()
                        .createTimeItem(
                            this._timeItem.getDateTimeValue()
                                .withZone(
                                    DateTimeZone.forOffsetHoursMinutes(
                                        this._timezone.getDurationValue().getHours(),
                                        this._timezone.getDurationValue().getMinutes()
                                    )
                                ),
                            true
                        );
                return ItemFactory.getInstance()
                    .createTimeItem(
                        this._timeItem.getDateTimeValue()
                            .withZoneRetainFields(
                                DateTimeZone.forOffsetHoursMinutes(
                                    this._timezone.getDurationValue().getHours(),
                                    this._timezone.getDurationValue().getMinutes()
                                )
                            ),
                        true
                    );
            }

        } else
            throw new IteratorFlowException(
                    RuntimeIterator.FLOW_EXCEPTION_MESSAGE + " adjust-time-to-timezone function",
                    getMetadata()
            );
    }

    @Override
    public void open(DynamicContext context) {
        super.open(context);
        this._timeItem = this._children.get(0).materializeFirstItemOrNull(this._currentDynamicContextForLocalExecution);
        if (this._children.size() == 2) {
            this._timezone = this._children.get(1)
                .materializeFirstItemOrNull(this._currentDynamicContextForLocalExecution);
        }
        this._hasNext = this._timeItem != null;
    }

    private boolean checkTimeZoneArgument() {
        return (Math.abs(this._timezone.getDurationValue().toDurationFrom(Instant.now()).getMillis()) > 50400000)
            ||
            (Double.compare(
                this._timezone.getDurationValue().getSeconds()
                    + this._timezone.getDurationValue().getMillis() * 1.0 / 1000,
                0
            ) != 0);
    }
}
