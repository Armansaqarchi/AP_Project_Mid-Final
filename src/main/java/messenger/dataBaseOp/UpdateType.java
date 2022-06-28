package messenger.dataBaseOp;

public enum UpdateType{

    ADD("Add"), REMOVE("Remove");

    private final String value;

    private UpdateType(String value){
        this.value = value;
    }

    public String showValue(){
        return value;
    }
}
