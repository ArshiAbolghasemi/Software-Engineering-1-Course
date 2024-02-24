package com.ut.se.messagingjms.lib.enums;

final public class SystemResponse {

    public static final class Deposit {

        public static final class Success {
            public static final int CODE = 0;
            public static final String ACTION = "Deposit";
            public static final String CONTEXT = "successful";
        }
    }

    public static final class Withdraw {
        public static final class Success {
            public static final int CODE = 0;
            public static final String ACTION = "Withdraw";
            public static final String CONTEXT = "successful";
        }

        public static final class Insufficient {
            public static final int CODE = 1;
            public static final String ACTION = "Insufficient";
            public static final String CONTEXT = "funds";
        }
    }

    public static final class Balance {
        public static final class Success {
            public static final int CODE = 0;
            public static final String ACTION = "Balance:";
        }
    }

    public static final class UnknownAccountNumber {
        public static final int CODE = 2;
        public static final String ACTION = "Unknown";
        public static final String CONTEXT = "account number";
    }
}
