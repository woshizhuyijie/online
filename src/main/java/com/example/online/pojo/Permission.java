package com.example.online.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
/*
*
 @author woshizhuyijie
 @date 2024-11-20
 074
* */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Permission {
    private Integer permissionId;
    private String permissionName;
    private Integer permissionCode;
    private String permissionUrl;
    private Integer permissionOrder;
}
