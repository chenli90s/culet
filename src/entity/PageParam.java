package entity;

/**
 * @author Chenli
 * @time 2017/3/8 16:14
 */
public class PageParam {

    private int firstNum;
    private int endNum;
    private String limitCondition;

    public PageParam(int requestNum, int currentPage, String limitCondition) {
        this.firstNum = requestNum*(currentPage-1);
        this.endNum = requestNum;
        this.limitCondition = limitCondition;
    }

    public int getFirstNum() {
        return firstNum;
    }

    public void setFirstNum(int firstNum) {
        this.firstNum = firstNum;
    }

    public int getEndNum() {
        return endNum;
    }

    public void setEndNum(int endNum) {
        this.endNum = endNum;
    }

    public String getLimitCondition() {
        return limitCondition;
    }

    public void setLimitCondition(String limitCondition) {
        this.limitCondition = limitCondition;
    }
}
