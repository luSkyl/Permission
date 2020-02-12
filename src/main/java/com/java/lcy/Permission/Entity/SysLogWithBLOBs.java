package com.java.lcy.Permission.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysLogWithBLOBs extends SysLog {
    private String oldValue;

    private String newValue;

    public void setOldValue(String oldValue) {
        this.oldValue = oldValue == null ? null : oldValue.trim();
    }


    public void setNewValue(String newValue) {
        this.newValue = newValue == null ? null : newValue.trim();
    }
}