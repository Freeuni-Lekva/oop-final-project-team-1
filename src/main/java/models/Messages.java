package models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Messages {
    public Map<String, ArrayList<Message>> messages;
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
    public boolean Compare(String from, String to, String message) {
        boolean result = false;
        if(messages.containsKey(to)) {
            for(Message i : messages.get(to)){
                if(i.from.equals(from) && i.message.equals(message)){
                    result = true;
                }
            }
        }
        return result;

    }
    public void removeMessage(Message mess) {
        messages.get(mess.to).remove(mess);
    }
    public  static class Message {
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
