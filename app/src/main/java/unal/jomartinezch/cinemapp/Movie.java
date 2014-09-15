package unal.jomartinezch.cinemapp;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Arrays;

/**
 * Created by Usuario on 10/09/2014.
 */
public class Movie implements Parcelable {
    final String name;
    final String mid;
    final boolean threeD;
    String duration;
    String genre;
    String director;
    String[] cast;

    public Movie(String name, String mid, boolean threeD){
        this.name = name;
        this.mid = mid;
        this.threeD = threeD;
        this.duration = "Unknown";
        this.genre = "Unknown";
        this.director= "Unknown";
    }

    @Override
    public String toString() {
        return "Movie [name=" + name + ", mid=" + mid + ", threeD="
                + threeD + ", duration=" + duration + ", genre=" + genre
                + ", director=" + director + ", cast=" + Arrays.toString(cast) + "]";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(mid);
        dest.writeByte((byte) (threeD ? 1 : 0));
        dest.writeString(duration);
        dest.writeString(genre);
        dest.writeString(director);
        dest.writeStringArray(cast);
    }

    private Movie(Parcel in){
        this.name = in.readString();
        this.mid = in.readString();
        this.threeD = in.readByte() != 0;
        this.duration = in.readString();
        this.genre = in.readString();
        this.director = in.readString();
        this.cast = in.createStringArray();
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {

        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}