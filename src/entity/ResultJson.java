package entity;

/**
 * @author Chenli
 * @time 2017/3/2 14:12
 */
public class ResultJson {
    private boolean errcode;
    private String msg;
    private String info;
    private Object data;

    public ResultJson(boolean errcode, String msg, String info) {
        this.errcode = errcode;
        this.msg = msg;
        this.info = info;
    }

    public ResultJson(boolean errcode, String msg) {
        this.errcode = errcode;
        this.msg = msg;
    }


    public ResultJson(boolean errcode, String msg, String info, Object data) {
        this.errcode = errcode;
        this.msg = msg;
        this.info = info;
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

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
