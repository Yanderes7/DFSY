package cn.synu.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User {
    private Long id;

    private String username;

    private String password;

    private Long identity;

    public User(String username ,String password ,Long identity){
        this.username = username;
        this.password = password;
        this.identity = identity;
    }
}