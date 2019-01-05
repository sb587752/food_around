package com.opalfire.foodorder.Pubnub;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MyMessage
{
  @Expose
  @SerializedName("message")
  private String message;
  @Expose
  @SerializedName("provider_id")
  private String providerId;
  @Expose
  @SerializedName("request_id")
  private Integer requestId;
  @Expose
  @SerializedName("type")
  private String type;
  @Expose
  @SerializedName("user_id")
  private Integer userId;
  
  public String getMessage()
  {
    return this.message;
  }
  
  public String getProviderId()
  {
    return this.providerId;
  }
  
  public Integer getRequestId()
  {
    return this.requestId;
  }
  
  public String getType()
  {
    return this.type;
  }
  
  public Integer getUserId()
  {
    return this.userId;
  }
  
  public void setMessage(String paramString)
  {
    this.message = paramString;
  }
  
  public void setProviderId(String paramString)
  {
    this.providerId = paramString;
  }
  
  public void setRequestId(Integer paramInteger)
  {
    this.requestId = paramInteger;
  }
  
  public void setType(String paramString)
  {
    this.type = paramString;
  }
  
  public void setUserId(Integer paramInteger)
  {
    this.userId = paramInteger;
  }
}
