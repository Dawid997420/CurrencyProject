package com.example.CurrencyProject.model;

public enum Group {
    A {
        public String getGroup() {
            return "A";
        }
    },
    a {
        public String getGroup() {
            return "A";
        }
    },
    b {
        public String getGroup() {
            return "B";
        }
    },
    B {
        public String getGroup() {
            return "B";
        }

    };

    public abstract String getGroup() ;

}
