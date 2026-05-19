package com.it.movie.recommendation.entity;

public class Movie {
    private Integer id;
    private String name;
    private String filename;
    private Integer categoryid;
    private Integer areaid;
    private String shstatus;
    private Category category;
    private Area area;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getFilename() { return filename; }
    public void setFilename(String filename) { this.filename = filename; }
    public Integer getCategoryid() { return categoryid; }
    public void setCategoryid(Integer categoryid) { this.categoryid = categoryid; }
    public Integer getAreaid() { return areaid; }
    public void setAreaid(Integer areaid) { this.areaid = areaid; }
    public String getShstatus() { return shstatus; }
    public void setShstatus(String shstatus) { this.shstatus = shstatus; }
    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }
    public Area getArea() { return area; }
    public void setArea(Area area) { this.area = area; }
}
