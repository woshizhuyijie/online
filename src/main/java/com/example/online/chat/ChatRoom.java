package com.example.online.chat;

public class ChatRoom {
    private Long roomId;
    private Long courseId;
    private List<Long> participantIds;
    private List<Message> messages;

    // inner class
    public static class Message {
        private Long senderId;
        private String content;
        private Date timestamp;
    }

    // getters & setters
}
