package com.funflowers;

import com.flexionmobile.codingchallenge.integration.Purchase;

import java.util.Objects;

public class MyPurchase implements Purchase {
    private String id;
    private boolean consumed;
    private String itemId;

    public void setId(String id) {
        this.id = id;
    }

    public void setConsumed(boolean consumed) {
        this.consumed = consumed;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public boolean getConsumed() {
        return consumed;
    }

    @Override
    public String getItemId() {
        return itemId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MyPurchase that = (MyPurchase) o;
        return consumed == that.consumed &&
                Objects.equals(id, that.id) &&
                Objects.equals(itemId, that.itemId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, consumed, itemId);
    }

    @Override
    public String toString() {
        return "MyPurchase{" +
                "id='" + id + '\'' +
                ", consumed=" + consumed +
                ", itemId='" + itemId + '\'' +
                '}';
    }
}
