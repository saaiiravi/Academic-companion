package models;

import com.google.firebase.database.IgnoreExtraProperties;
import com.google.gson.annotations.SerializedName;

/**
 * Created by menuka on 2/21/17. */

@IgnoreExtraProperties
public class Module {
    @SerializedName("module_id")
    private String moduleId;
    private String name;
    private String code;
    private String grade;
    private String credits;
    private boolean enabled;

    public Module() {}

    public Module(String code, String name, String grade, String credits) {
        this.name = name;
        this.code = code;
        this.grade = grade;
        this.credits = credits;
    }

    @Override
    public String toString() {
        return "Module{" +
                "moduleId='" + moduleId + '\'' +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", grade='" + grade + '\'' +
                ", credits='" + credits + '\'' +
                '}';
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getModuleId() {
        return moduleId;
    }

    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
    }

    public String getCredits() {
        return credits;
    }

    public void setCredits(String credits) {
        this.credits = credits;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }
}
