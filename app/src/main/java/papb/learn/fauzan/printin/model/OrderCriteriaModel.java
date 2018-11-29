package papb.learn.fauzan.printin.model;

public class OrderCriteriaModel {
    private String fileName,paperType,bindingType,frontColor,backColor;
    private boolean colored,binded;

    public OrderCriteriaModel() {

    }

    public OrderCriteriaModel(String fileName, String paperType, String bindingType, String frontColor, String backColor, boolean colored, boolean binded) {
        this.fileName = fileName;
        this.paperType = paperType;
        this.bindingType = bindingType;
        this.frontColor = frontColor;
        this.backColor = backColor;
        this.colored = colored;
        this.binded = binded;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getPaperType() {
        return paperType;
    }

    public void setPaperType(String paperType) {
        this.paperType = paperType;
    }

    public String getBindingType() {
        return bindingType;
    }

    public void setBindingType(String bindingType) {
        this.bindingType = bindingType;
    }

    public String getFrontColor() {
        return frontColor;
    }

    public void setFrontColor(String frontColor) {
        this.frontColor = frontColor;
    }

    public String getBackColor() {
        return backColor;
    }

    public void setBackColor(String backColor) {
        this.backColor = backColor;
    }

    public boolean isColored() {
        return colored;
    }

    public void setColored(boolean colored) {
        this.colored = colored;
    }

    public boolean isBinded() {
        return binded;
    }

    public void setBinded(boolean binded) {
        this.binded = binded;
    }
}
