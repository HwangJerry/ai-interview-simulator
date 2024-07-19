package soohyunj.interviewsimulator.infra;

public enum FileType {
    IMAGE("image"),
    AUDIO("audio"),
    VIDEO("video");

    private final String type;

    FileType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return type;
    }
}
