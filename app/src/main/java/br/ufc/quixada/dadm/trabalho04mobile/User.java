package br.ufc.quixada.dadm.trabalho04mobile;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {

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

    protected User(Parcel in) {
        uuid = in.readString();
        login = in.readString();
        profileURL = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getUuid() {
        return uuid;
    }

    public String getLogin() {
        return login;
    }

    public String getProfileURL() {
        return profileURL;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(uuid);
        dest.writeString(login);
        dest.writeString(profileURL);
    }
}
