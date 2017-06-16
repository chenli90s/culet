package entity;

/**
 * @author Chenli
 * @time 2017/6/15 18:20
 */
public class UploadFilesJson {
    int errno;
    String[] data;

    public UploadFilesJson(int errno, String[] data) {
        this.errno = errno;
        this.data = data;
    }

    public int getErrno() {
        return errno;
    }

    public void setErrno(int errno) {
        this.errno = errno;
    }

    public String[] getData() {
        return data;
    }

    public void setData(String[] data) {
        this.data = data;
    }
}
