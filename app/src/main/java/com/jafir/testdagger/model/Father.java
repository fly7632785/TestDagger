package com.jafir.testdagger.model;

import com.jafir.testdagger.db.MyFlowDB;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.OneToMany;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by jafir on 2017/3/24.
 */

@Setter
@Getter
@Table(database = MyFlowDB.class)
public class Father extends BaseModel {

    @PrimaryKey
    private Long id;
    @Column
    private String name;

    @ForeignKey
    User user;


    List<User> users;

    @OneToMany(methods = {OneToMany.Method.ALL}, variableName = "users")
    public List<User> getUsers() {
        if (users == null || users.isEmpty()) {
            users = SQLite.select().from(User.class).where(User_Table.fid.eq(id))
                    .queryList();
        }
        return users;
    }


    @Override
    public String toString() {
        return "Father{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", user=" + user +
                ", users=" + getUsers() +
                '}';
    }
}
