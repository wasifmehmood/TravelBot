package com.example.travelbot;

public class ServiceUtils {

        private Object name;
        private Object type;
        private Object price;
        private Object link;
        private Object from;
        private Object to;

        public ServiceUtils(Object name, Object type, Object price, Object link, Object from, Object to) {
            this.name = name;
            this.type = type;
            this.price = price;
            this.link = link;
            this.from = from;
            this.to = to;
        }

        public Object getName() {
            return name;
        }

        public void setName(Object name) {
            this.name = name;
        }

        public Object getType() {
            return type;
        }

        public void setType(Object type) {
            this.type = type;
        }

        public Object getPrice() {
            return price;
        }

        public void setPrice(Object price) {
            this.price = price;
        }

        public Object getLink() {
            return link;
        }

        public void setLink(Object link) {
            this.link = link;
        }

        public Object getFrom() {
            return from;
        }

        public void setFrom(Object from) {
            this.from = from;
        }

        public Object getTo() {
            return to;
        }

        public void setTo(Object to) {
            this.to = to;
        }

}
