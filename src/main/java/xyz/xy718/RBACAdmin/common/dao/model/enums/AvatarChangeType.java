package cloud.catisland.ivory.common.dao.model.enums;

public enum AvatarChangeType {
    EDIT(0),
    REMOVE(1);

    int value;
    AvatarChangeType(int type){
        this.value=type;
    }
    public int getValue(){
        return this.value;
    }
}