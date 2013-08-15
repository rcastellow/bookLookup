package com.example.BookSearch.data;

import android.util.JsonReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mark.mixson on 8/9/13.
 */
public abstract class JSONBinder<T> {
    private String topLevelNode;
    private List<String> fields;

    public JSONBinder(String topLevelNode, List<String> fields) {
        if (topLevelNode == null || fields == null) throw new NullPointerException();
        if (topLevelNode.isEmpty() || fields.isEmpty()) throw new IllegalArgumentException();
        this.topLevelNode = topLevelNode;
        this.fields = fields;
    }

    public List<T> getData(JsonReader reader) throws IOException {
        reader.beginObject();
        while (reader.hasNext()) {
            if (reader.nextName().equals(topLevelNode)) {
                return getItems(reader);
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return new ArrayList<T>();
    }

    private List<T> getItems(JsonReader reader) throws IOException {
        ArrayList<T> list = new ArrayList<T>();
        reader.beginArray();
        while(reader.hasNext()) {
            T item = getNextItem(reader);
            if (item != null)
                list.add(item);
        }
        reader.endArray();
        reader.endObject();
        return list;
    }

    private T getNextItem(JsonReader reader)  throws IOException {
        T item = getNewItem();
        reader.beginObject();
        item = updateItem(item, reader);
        reader.endObject();
        return item;
    }

    protected abstract T getNewItem();

    private T updateItem(T item, JsonReader reader) throws IOException {
        while(reader.hasNext()) {
            String fieldName = reader.nextName();
            if (fields.contains(fieldName)) {
                item = updateItemField(fieldName, item, reader);
            } else {
                reader.skipValue();
            }
        }
        return item;
    }

    protected abstract T updateItemField(String fieldName,
                                         T item,
                                         JsonReader reader) throws IOException;
}
