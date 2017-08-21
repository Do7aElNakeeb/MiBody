package ch.philopateer.mibody.object;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by NakeebMac on 10/1/16.
 */

public class ExerciseItem implements Parcelable{

    private String id;
    private String icon;
    private String image;
    private String GIF;
    private String description;
    private String name;
    private String category;

    public ExerciseItem(){

    }

    public ExerciseItem(String id, String name, String icon, String image, String GIF, String description, String category) {
        this.id = id;
        this.name = name;
        this.icon = icon;
        this.image = image;
        this.GIF = GIF;
        this.description = description;
        this.category = category;
    }


    protected ExerciseItem(Parcel in) {
        id = in.readString();
        icon = in.readString();
        image = in.readString();
        GIF = in.readString();
        description = in.readString();
        name = in.readString();
        category = in.readString();
    }

    public static final Creator<ExerciseItem> CREATOR = new Creator<ExerciseItem>() {
        @Override
        public ExerciseItem createFromParcel(Parcel in) {
            return new ExerciseItem(in);
        }

        @Override
        public ExerciseItem[] newArray(int size) {
            return new ExerciseItem[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getGIF() {
        return GIF;
    }

    public void setGIF(String GIF) {
        this.GIF = GIF;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(icon);
        parcel.writeString(image);
        parcel.writeString(GIF);
        parcel.writeString(description);
        parcel.writeString(name);
        parcel.writeString(category);
    }
}
