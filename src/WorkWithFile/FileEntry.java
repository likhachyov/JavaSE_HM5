package WorkWithFile;

class FileEntry {
    private int region;
    private String agencyName;
    private String post;
    private String name;
    private String numberType;
    private String number;

    public FileEntry(int region, String agencyName, String post, String name, String numberType, String number) {
        this.region = region;
        this.agencyName = agencyName;
        this.post = post;
        this.name = name;
        this.numberType = numberType;
        this.number = number;
    }

    public int getRegion() {
        return region;
    }
}
