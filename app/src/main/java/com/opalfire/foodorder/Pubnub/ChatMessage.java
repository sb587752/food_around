package com.opalfire.foodorder.Pubnub;

public class ChatMessage
{
  private String content;
  private boolean isImage;
  private boolean isMine;
  
  public ChatMessage(String paramString, boolean paramBoolean1, boolean paramBoolean2)
  {
    this.content = paramString;
    this.isMine = paramBoolean1;
    this.isImage = paramBoolean2;
  }
  
  public String getContent()
  {
    return this.content;
  }
  
  public boolean isImage()
  {
    return this.isImage;
  }
  
  public boolean isMine()
  {
    return this.isMine;
  }
  
  public void setContent(String paramString)
  {
    this.content = paramString;
  }
  
  public void setIsImage(boolean paramBoolean)
  {
    this.isImage = paramBoolean;
  }
  
  public void setIsMine(boolean paramBoolean)
  {
    this.isMine = paramBoolean;
  }
}
