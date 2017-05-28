package cn.zkdcloud.entity;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Set;

/**
 * @Author zk
 * @Date 2017/5/17.
 * @Email 2117251154@qq.com
 */
@Entity
public class User{

    @Id
    @GeneratedValue(generator="idGenerator")
    @GenericGenerator(name="idGenerator", strategy="uuid")
    private String uid;

    @Column
    private String username;

    @Column
    private String password;

    @Column
    private String email;

    @Column
    private String url;

    @ManyToOne()
    @JoinTable(name = "RoleUser",joinColumns = @JoinColumn(name = "uid"),
            inverseJoinColumns = @JoinColumn(name = "roleId"))
    private Role role;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "uid") //为了删除级联
    private Set<RoleUser> roleUserSet;

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}