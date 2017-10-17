package io.swagger.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Objects;

/**
 * api request,response none
 */
@ApiModel(description = "api request,response none")
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2017-09-17T06:54:37.818Z")

public class UserClientScope   {
  @JsonProperty("userCode")
  private int userCode;

  @JsonProperty("scopeId")
  private String scopeId = null;

  @JsonProperty("clientId")
  private String clientId = null;

  public UserClientScope userId(int userCode) {
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

  public UserClientScope scopeId(String scopeId) {
    this.scopeId = scopeId;
    return this;
  }

   /**
   * Get scopeId
   * @return scopeId
  **/
  @ApiModelProperty(value = "")
  public String getScopeId() {
    return scopeId;
  }

  public void setScopeId(String scopeId) {
    this.scopeId = scopeId;
  }

  public UserClientScope clientId(String clientId) {
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
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UserClientScope userClientScope = (UserClientScope) o;
    return Objects.equals(this.userCode, userClientScope.userCode) &&
        Objects.equals(this.scopeId, userClientScope.scopeId) &&
        Objects.equals(this.clientId, userClientScope.clientId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(userCode, scopeId, clientId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UserClientScope {\n");
    
    sb.append("    userCode: ").append(toIndentedString(userCode)).append("\n");
    sb.append("    scopeId: ").append(toIndentedString(scopeId)).append("\n");
    sb.append("    clientId: ").append(toIndentedString(clientId)).append("\n");
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

