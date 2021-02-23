package org.rumbledb.types;

import org.apache.spark.sql.types.DataType;
import org.apache.spark.sql.types.DataTypes;
import org.rumbledb.context.Name;

import java.util.Set;

/**
 * Class representing the generic 'item' item type
 */
public class ItemItemType implements ItemType {

    private static final long serialVersionUID = 1L;

    static final ItemType item = new ItemItemType(Name.createVariableInDefaultTypeNamespace("item"));
    private final Name name;

    private ItemItemType(Name name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        // no need to check the class because ItemItemType is a singleton and it is only equal to its only instance
        return o == item;
    }

    @Override
    public boolean hasName() {
        return true;
    }

    @Override
    public Name getName() {
        return this.name;
    }

    @Override
    public boolean isSubtypeOf(ItemType superType) {
        return superType == item;
    }

    @Override
    public ItemType findLeastCommonSuperTypeWith(ItemType other) {
        return item;
    }

    @Override
    public int getTypeTreeDepth() {
        return 0;
    }

    @Override
    public ItemType getBaseType() {
        return null;
    }

    @Override
    public Set<FacetTypes> getAllowedFacets() {
        throw new UnsupportedOperationException("item type does not support facets");
    }

    @Override
    public String toString() {
        return this.name.toString();
    }

    @Override
    public DataType toDataFrameType() {
        return DataTypes.BinaryType;
    }
}
