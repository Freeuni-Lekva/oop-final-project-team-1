package models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Messages {
    Map<String, ArrayList<Message>> messages;
    public Messages() {
        messages = new HashMap<>();
        messages.put("luka", new ArrayList<>());
        messages.put("gio", new ArrayList<>());
        Message mes = new Message("gio","luka","zdarova",false);
        messages.get("gio").add(mes);
        Message mes2 = new Message("luka","gio","zdarova",true);
        messages.get("luka").add(mes2);
    }
    public void addMessage(Message mess) {
        if (!messages.containsKey(mess.to)) {
            messages.put(mess.to, new ArrayList<>());
            messages.get(mess.to).add(mess);
        }else{
            messages.get(mess.to).add(mess);
        }
    }

    public ArrayList<Message> getMessages(String to) {
        if(messages.containsKey(to)) {
            return messages.get(to);
        }
        return new ArrayList<>();
    }
    public void removeMessage(Message mess) {
        messages.get(mess.to).remove(mess);
    }
    public class Message {
        public String to;
        public String from;
        public String message;
        public Boolean friendReq=false;
        public Message(String to, String from, String message,boolean friendReq) {
            this.to = to;
            this.from = from;
            this.message = message;
            this.friendReq=friendReq;
        }
    }
}
