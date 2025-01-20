package ru.otus.listener.homework;

import ru.otus.listener.Listener;
import ru.otus.model.Message;
import ru.otus.model.ObjectForMessage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class HistoryListener implements Listener, HistoryReader {

    private final Map<Long, Message> history = new HashMap<>();

    @Override
    public void onUpdated(Message msg) {
        var deepCopy = copyMessage(msg);
        history.put(msg.getId(), deepCopy);
    }

    @Override
    public Optional<Message> findMessageById(long id) {
        return Optional.ofNullable(history.get(id));
    }

    private Message copyMessage(Message original) {

        var objectForMessage = original.getField13() == null
                ? null
                : new ObjectForMessage();

        if (objectForMessage != null && original.getField13().getData() != null) {
            objectForMessage.setData(new ArrayList<>(original.getField13().getData()));
        }

        return new Message.Builder(original.getId())
                .field1(original.getField1())
                .field2(original.getField2())
                .field3(original.getField3())
                .field4(original.getField4())
                .field5(original.getField5())
                .field6(original.getField6())
                .field7(original.getField7())
                .field8(original.getField8())
                .field9(original.getField9())
                .field10(original.getField10())
                .field11(original.getField11())
                .field12(original.getField12())
                .field13(objectForMessage)
                .build();
    }
}
