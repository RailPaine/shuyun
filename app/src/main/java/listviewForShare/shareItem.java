package listviewForShare;

/**
 * Created by rail on 2016/7/8.
 */
public class shareItem {

    private int type;

    //private String imgPath;
    private int imgId;
    private String title;
    private String content;
    private String contentPath;

    public shareItem(int type, int imgId, String title, String content, String contentPath) {
        this.type = type;
        this.imgId = imgId;
        this.title = title;
        this.content = content;
        this.contentPath = contentPath;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContentPath() {
        return contentPath;
    }

    public void setContentPath(String contentPath) {
        this.contentPath = contentPath;
    }
}
