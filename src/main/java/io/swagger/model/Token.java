package io.swagger.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 
 */
@ApiModel(description = "")
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2017-09-17T06:54:37.818Z")

public class Token   {
  @JsonProperty("tokenId")
  private String tokenId = null;

  @JsonProperty("userCode")
  private int userCode;

  @JsonProperty("clientId")
  private String clientId = null;

  @JsonProperty("createTime")
  private String createTime = null;

  @JsonProperty("expireDate")
  private String expireDate = null;

  @JsonProperty("scopes")
  private List<String> scopes = new ArrayList<String>();

  public Token tokenId(String tokenId) {
    this.tokenId = tokenId;
    return this;
  }

   /**
   * Get tokenId
   * @return tokenId
  **/
  @ApiModelProperty(value = "")
  public String getTokenId() {
    return tokenId;
  }

  public void setTokenId(String tokenId) {
    this.tokenId = tokenId;
  }

  public Token userId(int userCode) {
    this.userCode = userCode;
    return this;
  }

   /**
   * Get userCode
   * @return userCode
  **/
  @ApiModelProperty(value = "")
  public int getUserCode() {
    return userCode;
  }

  public void setUserCode(int userCode) {
    this.userCode = userCode;
  }

  public Token clientId(String clientId) {
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

  public Token createTime(String createTime) {
    this.createTime = createTime;
    return this;
  }

   /**
   * Get createTime
   * @return createTime
  **/
  @ApiModelProperty(value = "")
  public String getCreateTime() {
    return createTime;
  }

  public void setCreateTime(String createTime) {
    this.createTime = createTime;
  }

  public Token expireDate(String expireDate) {
    this.expireDate = expireDate;
    return this;
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

  public Token scopes(List<String> scopes) {
    this.scopes = scopes;
    return this;
  }

  public Token addScopesItem(String scopesItem) {
    this.scopes.add(scopesItem);
    return this;
  }

   /**
   * Get scopes
   * @return scopes
  **/
  @ApiModelProperty(value = "")
  public List<String> getScopes() {
    return scopes;
  }

  public void setScopes(List<String> scopes) {
    this.scopes = scopes;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Token token = (Token) o;
    return Objects.equals(this.tokenId, token.tokenId) &&
        Objects.equals(this.userCode, token.userCode) &&
        Objects.equals(this.clientId, token.clientId) &&
        Objects.equals(this.createTime, token.createTime) &&
        Objects.equals(this.expireDate, token.expireDate) &&
        Objects.equals(this.scopes, token.scopes);
  }

  @Override
  public int hashCode() {
    return Objects.hash(tokenId, userCode, clientId, createTime, expireDate, scopes);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Token {\n");
    
    sb.append("    tokenId: ").append(toIndentedString(tokenId)).append("\n");
    sb.append("    userCode: ").append(toIndentedString(userCode)).append("\n");
    sb.append("    clientId: ").append(toIndentedString(clientId)).append("\n");
    sb.append("    createTime: ").append(toIndentedString(createTime)).append("\n");
    sb.append("    expireDate: ").append(toIndentedString(expireDate)).append("\n");
    sb.append("    scopes: ").append(toIndentedString(scopes)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

