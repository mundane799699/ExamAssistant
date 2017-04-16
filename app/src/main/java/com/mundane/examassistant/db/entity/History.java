package com.mundane.examassistant.db.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by mundane on 2017/4/16.
 */

@Entity
public class History {
    @Id(autoincrement = true)
    private Long id;
    private String section;
    private long index;
    private String description;
    @Generated(hash = 1695396410)
    public History(Long id, String section, long index, String description) {
        this.id = id;
        this.section = section;
        this.index = index;
        this.description = description;
    }
    @Generated(hash = 869423138)
    public History() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getSection() {
        return this.section;
    }
    public void setSection(String section) {
        this.section = section;
    }
    public long getIndex() {
        return this.index;
    }
    public void setIndex(long index) {
        this.index = index;
    }
    public String getDescription() {
        return this.description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
}
