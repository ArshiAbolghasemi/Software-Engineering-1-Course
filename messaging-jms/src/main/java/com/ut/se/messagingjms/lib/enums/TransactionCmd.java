package com.ut.se.messagingjms.lib.enums;

final public class TransactionCmd {

    public static class AmountActionCmd {
        public static final int ARGS_LENGTH  = 2;

        public static boolean isValidArgsStructure(String[] args) {
            return (args != null && args.length == ARGS_LENGTH);
        }

        public static String getAccountNo(String[] args) { return args[0]; }

        public static int getAmount(String[] args) {
            try {
                return Integer.parseInt(args[1]);
            } catch (NumberFormatException exception) {
                throw new IllegalArgumentException("Invalid amount!", exception);
            }
        }
    }

    public static final class Deposit extends AmountActionCmd {
        public static final String CMD = "DEPOSIT";
    }

    public static final class Withdraw extends AmountActionCmd {
        public static final String CMD = "WITHDRAW";
    }

    public static final class Balance {
        public static final String CMD = "BALANCE";

        public static final int ARGS_LENGTH = 1;

        public static boolean isValidArgsStructure(String[] args) {
            return (args != null && args.length == ARGS_LENGTH);
        }

        public static String getAccountNo(String[] args) { return args[0]; }
    }
}
