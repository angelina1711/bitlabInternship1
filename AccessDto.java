package bitlabInternship.lmsSystem.dto;

public class AccessDto {
    private Long id;
    private String level;

    public AccessDto() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}

