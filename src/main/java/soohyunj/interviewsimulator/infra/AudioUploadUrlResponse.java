package soohyunj.interviewsimulator.infra;

public record AudioUploadUrlResponse(
        String presignedUrl,
        String fileName
) {
    public static AudioUploadUrlResponse of(String presignedUrl, String fileName) {
        return new AudioUploadUrlResponse(presignedUrl, fileName);
    }
}
