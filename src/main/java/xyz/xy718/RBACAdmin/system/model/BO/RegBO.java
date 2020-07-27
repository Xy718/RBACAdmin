package cloud.catisland.ivory.system.model.BO;

import java.io.Serializable;

import javax.validation.constraints.Pattern;

import lombok.Data;


@Data
public class RegBO implements Serializable{
    private static final long serialVersionUID = -8836644929813047906L;
    
    @Pattern(regexp = "^[a-zA-Z0-9\\s\\S]{5,24}$",message = "账号长度在5-24个字符之间哦，包含大小写字母数字和特殊字符")
    private String username;

    @Pattern(regexp = "^[a-zA-Z0-9\\s\\S]{6,32}$",message = "密码长度在6-32个字符之间哦，包含大小写字母数字和特殊字符")
    private String password;
    
}