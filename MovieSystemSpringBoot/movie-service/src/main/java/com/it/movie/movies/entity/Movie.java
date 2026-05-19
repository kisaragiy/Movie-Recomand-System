package com.it.movie.movies.entity;

public class Movie {
    private Integer id;
    private String name;
    private String filename;
    private String videoname;
    private Integer categoryid;
    private Integer areaid;
    private String author;
    private String yeartime;
    private String playtime;
    private String content;
    private Integer cs;
    private Double score;
    private String isfree;
    private Double fee;
    private Integer zan;
    private String memberid;
    private String shstatus;
    private Category category;
    private Area area;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getFilename() { return filename; }
    public void setFilename(String filename) { this.filename = filename; }
    public String getVideoname() { return videoname; }
    public void setVideoname(String videoname) { this.videoname = videoname; }
    public Integer getCategoryid() { return categoryid; }
    public void setCategoryid(Integer categoryid) { this.categoryid = categoryid; }
    public Integer getAreaid() { return areaid; }
    public void setAreaid(Integer areaid) { this.areaid = areaid; }
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
    public String getYeartime() { return yeartime; }
    public void setYeartime(String yeartime) { this.yeartime = yeartime; }
    public String getPlaytime() { return playtime; }
    public void setPlaytime(String playtime) { this.playtime = playtime; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public Integer getCs() { return cs; }
    public void setCs(Integer cs) { this.cs = cs; }
    public Double getScore() { return score; }
    public void setScore(Double score) { this.score = score; }
    public String getIsfree() { return isfree; }
    public void setIsfree(String isfree) { this.isfree = isfree; }
    public Double getFee() { return fee; }
    public void setFee(Double fee) { this.fee = fee; }
    public Integer getZan() { return zan; }
    public void setZan(Integer zan) { this.zan = zan; }
    public String getMemberid() { return memberid; }
    public void setMemberid(String memberid) { this.memberid = memberid; }
    public String getShstatus() { return shstatus; }
    public void setShstatus(String shstatus) { this.shstatus = shstatus; }
    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }
    public Area getArea() { return area; }
    public void setArea(Area area) { this.area = area; }
}
