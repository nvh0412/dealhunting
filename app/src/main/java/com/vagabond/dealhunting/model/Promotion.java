package com.vagabond.dealhunting.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by HoaNV on 11/10/16.
 */

public class Promotion {
  private int id;

  private String title;

  @SerializedName("title_detail")
  private String titleDetail;

  private String slug;

  private String summary;

  @SerializedName("thumbnail_url")
  private String thumbnailUrl;

  private String image_url;

  private Date startDate;

  private Date endDate;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getTitleDetail() {
    return titleDetail;
  }

  public void setTitleDetail(String titleDetail) {
    this.titleDetail = titleDetail;
  }

  public String getSlug() {
    return slug;
  }

  public void setSlug(String slug) {
    this.slug = slug;
  }

  public String getSummary() {
    return summary;
  }

  public void setSummary(String summary) {
    this.summary = summary;
  }

  public String getThumbnailUrl() {
    return thumbnailUrl;
  }

  public void setThumbnailUrl(String thumbnailUrl) {
    this.thumbnailUrl = thumbnailUrl;
  }

  public String getImage_url() {
    return image_url;
  }

  public void setImage_url(String image_url) {
    this.image_url = image_url;
  }

  public Date getStartDate() {
    return startDate;
  }

  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }

  public Date getEndDate() {
    return endDate;
  }

  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }
}
