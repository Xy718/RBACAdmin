package cloud.catisland.ivory.system.model.pojo;

import java.io.Serializable;

import lombok.Data;

@Data
public class JWTModel implements Serializable{
    private static final long serialVersionUID = -883623429813047906L;
    String jwt;
}