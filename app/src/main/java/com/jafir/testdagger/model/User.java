package com.jafir.testdagger.model;

import com.jafir.testdagger.db.MyFlowDB;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by jafir on 2017/3/14.
 */
@Getter
@Setter
@ToString
@Table(database = MyFlowDB.class)
public class User extends BaseModel{

    @PrimaryKey(autoincrement = true)
    private Long id;

    @Column
    private String name;

    @Column
    private String age;

    @Column(name = "hobby")
    private String love;

    @Column
    private Long fid;


    public User(String name) {
        this.name = name;
    }

    public User() {
    }
}
