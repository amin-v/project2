package server;

public enum RsCode {
    successful(1), kambood_Mojoodi(2), incorrectInformation(3);

    RsCode(int value) {
        this.value = value;
    }

    private int value;

    public int getValue() {
        return value;
    }

}




