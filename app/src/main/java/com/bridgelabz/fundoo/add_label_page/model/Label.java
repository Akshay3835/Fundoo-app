package com.bridgelabz.fundoo.add_label_page.model;

public class Label {
    private int labelId;
    private String labelName;
    private String userId;


    public Label(int labelId, String labelName) {
        this.labelId = labelId;
        this.labelName = labelName;
    }

    public Label(String labelName)
    {
        this.labelName =labelName;
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("Label Id: ").append(labelId).append("\n")
                .append("Label Name: ").append(labelName).append("\n")
                .append("User Id: ").append(userId).append("\n\n");

        return sb.toString();
    }

    public int getLabelId()
    {
        return labelId;
    }

    public void setLabelId(int labelId)
    {
        this.labelId = labelId;
    }

    public String getLabelName()
    {
        return labelName;
    }

    public void setLabelName(String labelName)
    {
        this.labelName = labelName;
    }
    public String getUserId()
    {
        return userId;
    }
    public void setUserId(String userId)
    {
        this.userId = userId;
    }

}
