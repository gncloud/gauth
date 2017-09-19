package io.swagger.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Objects;

/**
 * pending response
 */
@ApiModel(description = "pending response")
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2017-09-17T12:30:43.496Z")

public class PendingUserResponse   {
  @JsonProperty("email")
  private String email = null;

  @JsonProperty("activateKey")
  private String activateKey = null;

  @JsonProperty("expireDate")
  private String expireDate = null;

  @JsonProperty("retryUrl")
  private String retryUrl = null;

  @JsonProperty("clientId")
  private String clientId = null;

  @JsonProperty("status")
  private String status = null;


  public PendingUserResponse email(String email) {
    this.email = email;
    return this;
  }

  /**
   * Get email
   * @return email
   **/
  @ApiModelProperty(value = "")
  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public PendingUserResponse activateKey(String activateKey) {
    this.activateKey = activateKey;
    return this;
  }

  /**
   * Get activateKey
   * @return activateKey
   **/
  @ApiModelProperty(value = "")
  public String getActivateKey() {
    return activateKey;
  }

  public void setActivateKey(String activateKey) {
    this.activateKey = activateKey;
  }

  public PendingUserResponse expireDate(String expireDate) {
    this.expireDate = expireDate;
    return this;
  }

  /**
   * Get status
   * @return status
   **/
  @ApiModelProperty(value = "")
  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }


  /**
   * Get expireDate
   * @return expireDate
   **/
  @ApiModelProperty(value = "")
  public String getExpireDate() {
    return expireDate;
  }

  public void setExpireDate(String expireDate) {
    this.expireDate = expireDate;
  }

  public PendingUserResponse retryUrl(String retryUrl) {
    this.retryUrl = retryUrl;
    return this;
  }

  /**
   * Get retryUrl
   * @return retryUrl
   **/
  @ApiModelProperty(value = "")
  public String getRetryUrl() {
    return retryUrl;
  }

  public void setRetryUrl(String retryUrl) {
    this.retryUrl = retryUrl;
  }

  public PendingUserResponse clientId(String clientId) {
    this.clientId = clientId;
    return this;
  }

  /**
   * Get clientId
   * @return clientId
   **/
  @ApiModelProperty(value = "")
  public String getClientId() {
    return clientId;
  }

  public void setClientId(String clientId) {
    this.clientId = clientId;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PendingUserResponse pendingUserResponse = (PendingUserResponse) o;
    return Objects.equals(this.email, pendingUserResponse.email) &&
            Objects.equals(this.activateKey, pendingUserResponse.activateKey) &&
            Objects.equals(this.expireDate, pendingUserResponse.expireDate) &&
            Objects.equals(this.retryUrl, pendingUserResponse.retryUrl) &&
            Objects.equals(this.status, pendingUserResponse.status) &&
            Objects.equals(this.clientId, pendingUserResponse.clientId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(email, activateKey, expireDate, retryUrl, clientId, status);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PendingUserResponse {\n");

    sb.append("    email: ").append(toIndentedString(email)).append("\n");
    sb.append("    activateKey: ").append(toIndentedString(activateKey)).append("\n");
    sb.append("    expireDate: ").append(toIndentedString(expireDate)).append("\n");
    sb.append("    retryUrl: ").append(toIndentedString(retryUrl)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    clientId: ").append(toIndentedString(clientId)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

