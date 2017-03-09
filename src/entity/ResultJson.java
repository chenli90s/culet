package entity;

/**
 * @author Chenli
 * @time 2017/3/2 14:12
 */
public class ResultJson {
    private boolean errcode;
    private String msg;
    private String name;
    private Object data;

    public ResultJson(boolean errcode, String msg, String name) {
        this.errcode = errcode;
        this.msg = msg;
        this.name = name;
    }

    public ResultJson(boolean errcode, String msg) {
        this.errcode = errcode;
        this.msg = msg;
    }

    public ResultJson(boolean errcode, String msg, String name, Object data) {
        this.errcode = errcode;
        this.msg = msg;
        this.name = name;
        this.data = data;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public ResultJson(int code, String errmsg){
        if (code==0){
            this.errcode = true;
        }else{
            this.errcode = false;
        }

        this.msg = errmsg;
    }

    public boolean isErrcode() {
        return errcode;
    }

    public void setErrcode(boolean errcode) {
        this.errcode = errcode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
