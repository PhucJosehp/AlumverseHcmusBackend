package hcmus.alumni.counsel.common;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class PostAdvisePermissions implements Serializable {
    private static final long serialVersionUID = 1L;

    private Boolean edit;
    private Boolean delete;
}