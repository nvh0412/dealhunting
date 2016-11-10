package com.vagabond.dealhunting.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by HoaNV on 11/10/16.
 */

public class Store {
  private int id;

  private String title;

  @SerializedName("thumbnail_url")
  private String thumbnailUrl;

  private String slug;

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

  public String getSlug() {
    return slug;
  }

  public void setSlug(String slug) {
    this.slug = slug;
  }

  public String getThumbnailUrl() {
    return thumbnailUrl;
  }

  public void setThumbnailUrl(String thumbnailUrl) {
    this.thumbnailUrl = thumbnailUrl;
  }
}
