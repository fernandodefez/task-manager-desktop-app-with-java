package com.fernandodefez.app.models;

public class Task implements Model {

   private Integer id = null;
   private String title;
   private String description;
   private boolean isDone;
   private String createdAt;

   /**
    * This constructor is used when fetching a task from the database
    */
   public Task(Integer id, String title, String description, boolean isDone) {
      this.id = id;
      this.title = title;
      this.description = description;
      this.isDone = isDone;
   }

   /**
    * This constructor was created so the task's fields can be shown on the table
    */
   public Task(Integer id, String title, String description, boolean isDone, String createdAt) {
      this.id = id;
      this.title = title;
      this.description = description;
      this.isDone = isDone;
      this.createdAt = createdAt;
   }

   /**
    * This constructor is used when creating a task for the first time
    */
   public Task(String title, String description) {
      this.title = title;
      this.description = description;
   }

   public Task() {
      this.title = "";
      this.description = "";
      this.isDone = false;
   }

   public Integer getId() {
      return id;
   }

   public void setId(Integer id) {
      this.id = id;
   }

   public String getTitle() {
      return title;
   }

   public void setTitle(String title) {
      this.title = title;
   }

   public String getDescription() {
      return description;
   }

   public void setDescription(String body) {
      this.description = body;
   }

   public boolean isDone() {
      return isDone;
   }

   public void setIsDone(boolean isDone) {
      this.isDone = isDone;
   }

   public String getCreatedAt() {
      return createdAt;
   }

   public void setCreatedAt(String createdAt) {
      this.createdAt = createdAt;
   }
}
