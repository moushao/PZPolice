package cn.pedant.SweetAlert;

/**
 * Created by Administrator on 2017/3/28.
 * 服务端返回的z地字典数据格式
 */

public class DictionariesEntity {

    /**
     * ID : 5001
     * NAME : 公休
     */

    private String ID;
    private String NAME;

    public DictionariesEntity(String ID, String NAME) {
        this.ID = ID;
        this.NAME = NAME;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }
}
