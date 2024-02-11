package com.example.lab5;

public class TodoItem {
        private long id;
        private String text;
        private boolean urgent;
        public TodoItem(String text, boolean urgent) {
            this.text = text;
            this.urgent = urgent;
        }
        public long getId() {
            return id;
        }
        public void setId(long id) {
            this.id = id;
        }
        public String getText() {
            return text;
        }
        public boolean isUrgent() {
            return urgent;
        }
    }
