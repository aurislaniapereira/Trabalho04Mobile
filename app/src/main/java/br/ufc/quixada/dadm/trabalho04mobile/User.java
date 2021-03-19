package br.ufc.quixada.dadm.trabalho04mobile;

public class User {

    private String uuid;
    private String login;
    private String profileURL;

    public User (){

    }

    public User(String uuid, String login, String profileURL) {
        this.uuid = uuid;
        this.login = login;
        this.profileURL = profileURL;
    }

    public String getUuid() {
        return uuid;
    }

    public String getLogin() {
        return login;
    }

    public String getProfileURL() {
        return profileURL;
    }
}
